
function element(x,y,x2,y2){
    return [x,y,x2,y2].join(" ")    
}
function inverter(x,y,x2,y2,f,sr,hv){
    return "I "+element(x,y,x2,y2)+" "+[f,sr,hv].join(" ")
}


function inverter(x,y,x2,y2,f,sr,hv){
    return "I "+element(x,y,x2,y2)+" "+[f,sr,hv].join(" ")

function input(){
teste ="I 0 176 112 176 0 0.5 5 L 0 176 0 128 2 0 false 5 0 w 0 176 0 464 0 w 112 176 112 464 0 x -4 99 12 102 4 24 A"
}


console.log(inverter(0,10,0,20,0,5,5,5))
