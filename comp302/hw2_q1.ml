(*
David Gilbert 
260746680

Daniel Chernis
260707258
*)
type prop = Atom of string
          | Not of prop
          | And of prop * prop
          | Or of prop * prop


let impl (p, q) = Or(Not p, q)
let iff (p, q) = And (impl (p,q), impl (q, p))

let mp = impl (And (Atom "P", impl (Atom "P", Atom "Q")), Atom "Q")

(* Proof by contradiction (reduction ad absurdum): ((¬ P) ⇒ Q) ∧ ((¬ P) ⇒ (¬ Q)) ⇒ P *)
let ra = impl (
             And (impl (Not (Atom "P"),
                        Atom "Q"),
                  impl (Not (Atom "P"),
                        Not (Atom "Q"))),
             Atom "P")

(* Atoms and their negations *)
type signed_atom
  = PosAtom of string
  | NegAtom of string

(* In NNF negations can only be applied to atoms, this datatype only
   allows for propositions in NNF *)
type nnf
  = AndN of nnf * nnf
  | OrN of nnf * nnf
  | AtomN of signed_atom

(* Q1.2: Write the function nnf that converts propositions into NNF,
   notice that the typechecker will guide you in your answer as the
   type forces the result to be in NNF. Your job is to not forget any
   sub-parts of the proposition in the conversion. *)


 (*receves any proposition and make it so that there is only negation on atoms *)
let rec to_nnf : prop -> nnf = function
  | Atom a -> AtomN (PosAtom a)
  | Not (Atom a) -> AtomN (NegAtom a)
  | Not (Not (Atom p)) -> AtomN (PosAtom p)
  | Not (And (p, q)) -> to_nnf (Or (Not p, Not q))
  | Not (Or (p, q)) -> to_nnf (And (Not p, Not q))
  | And (p, q) -> AndN (to_nnf p, to_nnf q)
  | Or (p, q) -> OrN (to_nnf p, to_nnf q)

(*let prop2 =And (Not (And (Atom "a",Atom "b")),Atom "b")
to_nnf prop2*)


(* Q1.3: Write a datatype cnf that represents only propositions in
   cnf. Hint: You might want to use more than one type to be able to
   represent sub-expressions.*)

type conjunction =
  OrC of conjunction*conjunction
  |Atomc of signed_atom

type cnf =
  AndC of cnf*cnf
  |AtomC of conjunction



(* Q1.4: Write the distribute and nnf_to_cnf functions using the new
   datatype. Hint: you may need more than one helper function. *)


let rec distribute (c1,c2) : cnf = match (c1,c2) with
  | p, AndC (q, r) -> AndC (distribute (p, q), distribute (p, r))
  | AndC(q, r), p -> AndC (distribute (q, p), distribute (r, p))
  | AtomC p, AtomC q -> AtomC ( OrC (p, q) )



let rec nnf_to_cnf prop : cnf = match prop with
  | AtomN (PosAtom a) -> AtomC (Atomc (PosAtom a) )
  | AtomN (NegAtom a) -> AtomC (Atomc (NegAtom a) )
  | OrN(p, q) -> distribute (nnf_to_cnf p, nnf_to_cnf q)
  | AndN(p, q) -> AndC (nnf_to_cnf p, nnf_to_cnf q)



let to_cnf (p :prop) : cnf = nnf_to_cnf (to_nnf p)


(* Q1.5: Write the new positives and negative atoms function as in the
   previous version from proposition in CNF they return  the positive atoms in a prep*)




let rec positives prop = match prop with
  | AtomC ( Atomc (PosAtom a) ) -> [a]
  | AtomC ( Atomc (NegAtom a) ) -> []
  | AtomC ( OrC (p, q) ) -> positives (AtomC p) @ positives (AtomC q)
  | AndC ( p,q ) ->  positives p @ positives q


let rec negatives prop = match prop with
  | AtomC ( Atomc (PosAtom a) ) -> []
  | AtomC ( Atomc (NegAtom a) ) -> [a]
  | AtomC ( OrC (p, q) ) -> negatives (AtomC p) @ negatives (AtomC q)
  | AndC ( p,q ) ->  negatives p @ negatives q

(* Fill in the code for the intersection function from Q1.1 *)


let rec intersection l1 l2 =
  let rec contains element l = match l with
  |[] -> false
  |[head] -> if head = element then true else false
  |head::t -> if head = element then true else contains element t
  in let rec constructor l1 l2 acc= match l1 with
   |[] ->[]
   |[h] -> if contains h l2 && not (contains h acc) then [h] else []
   |h::t -> if contains h l2 && not (contains h acc) then h :: (constructor t l2 (h::acc))  else constructor t l2 acc
  in constructor l1 l2 []


(* Q1.6: Write the new cnf_tautology function *)
let rec cnf_tautology (prop:cnf) = match prop with
  | AndC (p, q) -> cnf_tautology p && cnf_tautology q
  | p -> not ([] = intersection (positives p) (negatives p))

let taut (p : prop) : bool = cnf_tautology (to_cnf p)
let unsat (p : prop) : bool = taut (Not p)
let sat (p : prop) : bool = not (unsat p)
