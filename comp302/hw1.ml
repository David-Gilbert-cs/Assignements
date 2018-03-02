(* Student information:

   Enter your name, and if you chose to work in pairs, the name of the
   student you worked with (both students MUST submit the solution to
   myCourses):

   Name:David Gilbert
   McGill ID: 260746680

   If you worked in pairs, the name of the other student.

   Name:Daniel Chernis
   McGill ID:260707258


 *)

(* Notice: by submitting as part of team, you declare that you worked
   together on the solution. Submissions in pairs are allowed to
   foster team work, they have to be developed by both students *)

(* Homework 1 - Questions 2 and 3 *)

(* First, some utility functions and declarations that you can use. Be
   sure to check Ocaml's documentation to find more functions
   available to you.

   You can start checking the documentation at:
   https://caml.inria.fr/pub/docs/manual-ocaml/libref/Pervasives.html
 *)

(* the value of pi *)
let pi : float =  acos ~-.1.0

(* a function to compare floats that allows for some imprecision *) (*return true if  equal, usefulll for iscocel equi triangle *)
let cmp n m = abs_float (n -. m) < 0.0001

(* a simple test of positivity *)
let positive a = a > 0.0

(* a function to check if a is multiple of b *)
let is_multiple_of (a : float) (b : float) : bool =
  let m = a /. b in
  cmp (m -. floor m) 0.0

(* a function to check if a is between plus/minus b *)
let abs_btwn a b = a < b && a > ~-.b

(* Question 2: Triangles are the best *)

type side = float

type tr_by_sides = side * side * side

type tr_kind
  = Scalene
  | Equilateral
  | Isosceles

(* Question 2.1 *)


let well_formed_by_sides (a, b, c : tr_by_sides) : 
    bool = 
    if a+.b>c && a+.c>b && b+.c>a then true else false


(* Question 2.2 *)

let create_triangle (kind : tr_kind) (area : float) : tr_by_sides = match kind with
  | Equilateral -> let a = sqrt( area *. 4.0 /. sqrt 3.0) in (a,a,a)
  | Isosceles -> let a= sqrt(4.0*.(area**2.0)+.0.25) in (1.0,a,a)
  | Scalene -> if area = 1.0 then let h = 4.0 *. area in (0.5, h, sqrt(h**2.0 +. 0.25)) 
	     else let h = 2.0 *. area in (1.0,h,sqrt(h**2.0 +. 1.0))

(* Question 2.3 *)
type angle = float

type tr_by_angle = side * side * angle

let well_formed_by_angle (a, b, gamma) : bool =
  (positive a && positive b && positive gamma) &&
    (not (is_multiple_of gamma pi))

let sides_to_angle (a, b, c : tr_by_sides) : tr_by_angle option=
  if well_formed_by_sides(a,b,c) then Some ( let gamma = acos( (0.0 -. c**2.0 +. a**2.0 +. b**2.0) /.( 2.0 *. a *. b) ) in (a,b,gamma) ) 
  else None

let angle_to_sides (a, b, gamma) : tr_by_angle option =
    if well_formed_by_angle(a,b,gamma) then Some( let c = sqrt (a**2.0 +. b **2.0 -. 2.0 *. a *. b *. cos gamma)  in (a,b,c) )
    else None

(* Now that you implemented Q2.2 and saw the new representation of
   triangles by two sides and the angle between them, also ponder on
   how one represents Q2.2 using this representation. The idea is to
   think about how the representation helps make illegal states hard
   to represent and how easy and hard it is to implement the
   algorithm. *)

(* Question 3: Flexing recursion and lists *)

let even (n : int) : bool = n mod 2 = 0

(* Question 3.1 *)

let evens_first (l : int list) : int list = 
 let rec separate l ev un = match l with 
 |[] -> ev @ un
 |[h] -> if even h then ev @ [h] @ un else ev @ un @ [h]
 |h::t -> if even h then let ev = ev @ [h] in separate t ev un else let un = un @ [h] in separate t ev un 
 in separate l [] [] 







let ex_1 = evens_first [7 ; 5 ; 2; 4; 6; 3; 4; 2; 1]
(* val ex_1 : int list = [2; 4; 6; 4; 2; 7; 5; 3; 1] *)

(* Question 3.2 *)

let even_streak (l:int list): int =
    let rec seq l n n0 = match l with
    | [] -> n
    | [x] -> if even x then 
                        begin if (n0+1) > n then n0+1 else n end
            else if (n0>n) then n0 else n                
    | h::t -> if even h then seq t n (n0+1)
              else if n0>n then seq t n0 0 else seq t n 0
    in 
    seq l 0 0

let ex_2 = even_streak [7; 2; 4; 6; 3; 4; 2; 1]

(* val ex_2 : int = 3 *)


(* Question 3.3 *)

let even (n : int) : bool = n mod 2 = 0

type nucleobase = A | G | C | T


let compress (l : nucleobase list) : (int * nucleobase) list = match l with
| [] -> []
| head::tail ->
  let rec count l k n = match l with
  | [] -> (k,n)::[]
  | h::t -> if h = n then count t (k+1) h
            else (k,n) :: count t 1 h
  in count l 0 head


let rec decompress (l : (int * nucleobase) list) : nucleobase list = match l with
 |[] -> []
 |(num,nuc)::t ->
  let rec temp l k n = match l with
  | [] ->  n::[] 
  | (num,nuc)::t -> if k = 0 then temp t num nuc
                 else n :: temp l (k-1) n 
  in temp l 0 nuc 



let sample_dna : nucleobase list = [A;A;A;A;G;G;A;T;T;T;C;T;C]

let ex_3 = compress sample_dna

let ex_4 = decompress ex_3

let res_3_4 = sample_dna = ex_4 (* This should be true if everything went well *)
