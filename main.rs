//Author: Samarth M.
use std::time::Duration;
fn main()
{
    let pass=String::from("AVHBCKHWEBFKVHBAKVMGHBAEKBCACBHDKVAHVCAKGHJNB");
    let len_p=pass.len();
    //guess refers to the input password
    let mut guess=String::new();
    let mut flag=0;
    loop
    {
        let s = std::time::Instant::now();
        let mut t:Duration=s.duration_since(s);
        //t stores a reference of duration=0, to compare against.
        let mut ch:char='A';
        let mut correct:u8;//stores result of verify
        let mut times=[t;26];
        let mut counter;//to keep track of index of times
        for _j in 0..1000
        {
            counter=0;
            for i in 'A'..'['
            {
                guess.push(i);
                if guess.len()>len_p//to prevent infinite looping
                {
                    flag=1;
                    break;
                }
                let start = std::time::Instant::now();
                correct=verify(&guess[..], &pass[..], len_p);
                let end = std::time::Instant::now();
                let time = end.duration_since(start);
                times[counter]+=time;
                if correct==1//password obtained
                {
                    println!("{guess}");
                    flag=1;
                    break;
                }
                guess.pop();
                counter+=1;
            }
            if flag==1
            {
                break;
            }
        }
        if flag==1
        {
            break;
        }
        counter=0;
        for i in 'A'..'['//to find character with maximum time.
        {
            if times[counter]>t
            {
                t=times[counter];
                ch=i;
            }
            guess.push(i);
            println!("{:?} {guess}",times[counter]);
            guess.pop();
            //the previous 3 lines are just to visually track execution.
            //It can be removed.
            counter+=1;
        }
        guess.push(ch);   
    }
}
//translated verify function.
fn verify(guess:&str,pass:&str,len_p:usize)->u8
{
    let len_d=guess.len();
    let mut correct:u8=1;
    let mut matc:usize=0;
    let mut i: usize=0;
    while i<len_d && i<len_p
    {
        if pass[i..i+1]==guess[i..i+1]
        {
            matc+=1;
            for _j in 0..5000
            {}
        }
        else
        {
            break;
        }
        i+=1;
    }
    if matc!=len_p
        {
            correct=0;
        }
    return correct;
}