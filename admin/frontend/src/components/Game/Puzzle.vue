<template>
    <canvas ref="myCanvas" style="border:2px solid #000;background:#fff;"
    @click="handleClick"
    @mousemove="handleMove"
    @touchstart="touchClick"
    @touchmove="touchMove"

    ></canvas>
</template>

<script setup>

import dimetrodonImg from "@/assets/puzzle/dimetrodon.jpg";

const { proxy } = getCurrentInstance();
const canvas = ref();
const ctx = ref();
const img = ref();

const boardSize = ref(0);
const tileCount = ref(4);

const tileSize = ref(0);
const clickLoc = ref({
    x: 0,
    y: 0
});
const emptyLoc = ref({
    x: 0,
    y: 0
});

const solved = ref(false);

const boardParts = ref([]);


onMounted(()=>{
    
    canvas.value = proxy.$refs.myCanvas;
    ctx.value = canvas.value.getContext('2d');

    img.value = new Image();
    img.value
    img.value.src = dimetrodonImg;

    img.value.onload = function(){
        
        let width = img.value.width;
        let height = img.value.height;
        let v = width>height?height:width;

        canvas.value.width = v;
        canvas.value.height = v;
        
        boardSize.value = canvas.value ? canvas.value.width : 0;
        tileSize.value = boardSize.value / tileCount.value;
        
        setBoard();
        
        drawTiles();

    };

});

function setBoard() {
    
    boardParts.value = []

    for (var i = 0; i < tileCount.value; ++i) {
        let row = [];

        for (var j = 0; j < tileCount.value; ++j) {

            let obj = {
                x: (tileCount.value - 1) - i,
                y: (tileCount.value - 1) - j
            }
            row.push(obj);                
        }
        boardParts.value.push(row);
    }
    
    emptyLoc.value.x = boardParts.value[tileCount.value - 1][tileCount.value - 1].x;
    emptyLoc.value.y = boardParts.value[tileCount.value - 1][tileCount.value - 1].y;
    
    solved.value = false;
}


function drawTiles() {
    
    // 绘制一些内容
    // ctx.value.fillStyle = 'blue';
    // ctx.value.fillRect(50, 50, 150, 100); // 绘制一个蓝色长方形
    // ctx.value.fillStyle = 'green';
    // ctx.value.fillRect(250, 50, 150, 100); // 绘制一个绿色长方形

    ctx.value.clearRect( 0 , 0 , boardSize.value , boardSize.value );
    //ctx.value.drawImage(img.value, 0, 0, boardSize.value, boardSize.value);
    
    for (var i = 0; i < tileCount.value; ++i) {

        for (var j = 0; j < tileCount.value; ++j) {

            var x = boardParts.value[i][j].x;
            var y = boardParts.value[i][j].y;

            if(i != emptyLoc.value.x || j != emptyLoc.value.y || solved.value == true) {

                 ctx.value.drawImage(img.value, x * tileSize.value, y * tileSize.value, tileSize.value, tileSize.value,
                     i * tileSize.value, j * tileSize.value, tileSize.value, tileSize.value);
                
            }
        
        }

    }

}

function distance(x1, y1, x2, y2) {

    let value = Math.abs(x1 - x2) + Math.abs(y1 - y2);

    return value;
}

function slideTile(toLoc, fromLoc) {
    if (!solved.value) {

        boardParts.value[toLoc.value.x][toLoc.value.y].x = boardParts.value[fromLoc.value.x][fromLoc.value.y].x;
        boardParts.value[toLoc.value.x][toLoc.value.y].y = boardParts.value[fromLoc.value.x][fromLoc.value.y].y;

        boardParts.value[fromLoc.value.x][fromLoc.value.y].x = tileCount.value - 1;
        boardParts.value[fromLoc.value.x][fromLoc.value.y].y = tileCount.value - 1;
        
        toLoc.value.x = fromLoc.value.x;
        toLoc.value.y = fromLoc.value.y;

        checkSolved();
    }
}

function checkSolved() {
    var flag = true;
    for (var i = 0; i < tileCount.value; ++i) {
        for (var j = 0; j < tileCount.value; ++j) {
            if (boardParts.value[i][j].x != i || boardParts.value[i][j].y != j) {
                flag = false;
            }
        }
    }
    solved.value = flag;
}


function handleClick(){
    
    if (distance(clickLoc.value.x, clickLoc.value.y, emptyLoc.value.x, emptyLoc.value.y) == 1) {
        slideTile(emptyLoc, clickLoc);
        drawTiles();
    }
    if (solved.value) {
        setTimeout(function() {alert("You solved it!");}, 500);
    }
}

function handleMove(e){
    clickLoc.value.x = Math.floor((e.pageX) / tileSize.value);
    clickLoc.value.y = Math.floor((e.pageY) / tileSize.value);
}


function touchClick(el){
    
    var e = el.changedTouches[0];

    clickLoc.value.x = Math.floor((e.pageX) / tileSize.value);
    clickLoc.value.y = Math.floor((e.pageY) / tileSize.value);
    
    if (distance(clickLoc.value.x, clickLoc.value.y, emptyLoc.value.x, emptyLoc.value.y) == 1) {
        slideTile(emptyLoc, clickLoc);
        drawTiles();
    }
    if (solved.value) {
        setTimeout(function() {alert("You solved it!");}, 500);
    }

    el.preventDefault();
}

function touchMove(e){
    console.log("22222",e);
}

</script>