<template>
    <div style="height: 100%; display: flex; justify-content: center; align-items: center;">
        <canvas ref="myCanvas" height="453px" width="340px" style="border:2px solid #000;background:#fff;"
        @touchstart="handleTouchStart"
        @touchend="handleTouchEnd"
        @click="handleClick"></canvas>
    </div>
</template>
<script setup>

    import startImg from "@/assets/bird/start.jpg";
    import bgImg from "@/assets/bird/bg.png";
    import birdImg from "@/assets/bird/bird.png";
    import obsImg from "@/assets/bird/obs.png"
    import overImage from "@/assets/bird/over.png"

    const { proxy } = getCurrentInstance();
    const game = ref();
    const canvas = ref();
    const ctx = ref();

    var Speed = 20;
    var IsPlay = false;
    var GameTime = null;    

    function Bird(x, y, image) {
        this.x = x,
        this.y = y,
        this.width = image.width / 2,
        this.height = image.height,
        this.image = image;
        this.draw = function(context, state) {
            if (state === "up")
                context.drawImage(image, 0, 0, this.width, this.height, this.x, this.y, this.width, this.height);
            else{
                context.drawImage(image, this.width, 0, this.width, this.height, this.x, this.y, this.width, this.height);
            }
        }
    };

    function Obstacle(x, y, h, image) {
        this.x = x,
        this.y = y,
        this.width = image.width / 2,
        this.height = h,
        this.flypast=false;//没被飞过
        this.draw = function(context, state) {
            if (state === "up") {
                context.drawImage(image, 0, 0, this.width, this.height, this.x, this.y, this.width, this.height);
            } else {
                context.drawImage(image, this.width, image.height - this.height, this.width, this.height, this.x, this.y, this.width, this.height);
            }
        }
    };


    function FlappyBird() {}
    FlappyBird.prototype = {
        bird: null, // 小鸟
        bg: null, // 背景图
        obs: null, // 障碍物
        obsList: [],

        mapWidth: 340, // 画布宽度
        mapHeight: 453, // 画布高度
        startX: 90, // 起始位置  
        startY: 225,
        obsDistance: 150, // 上下障碍物距离  
        obsSpeed: 2, // 障碍物移动速度  
        obsInterval: 2000, // 制造障碍物间隔ms  
        upSpeed: 8, // 上升速度  
        downSpeed: 3, // 下降速度  
        line: 56, // 地面高度
        score: 0, // 得分  
        touch: false, // 是否触摸
        gameOver: false,

        CreateMap: function() { 
            this.bg = new Image();
		    this.bg.src = bgImg;
            
            var startBg = new Image();
		    startBg.src = startImg;
		    // 由于Image异步加载, 在加载完成时在绘制图像
            startBg.onload = function(){
                ctx.value.drawImage(startBg, 0, 0);
            };

            
            //小鸟
            var image = new Image();
            image.src = birdImg;		
            image.onload = function(){
                this.bird = new Bird(this.startX, this.startY, image);
            }.bind(this);

            //障碍物  
            this.obs = new Image();
            this.obs.src = obsImg;
            this.obs.onload = function() {
                var h = 100; // 默认第一障碍物上管道高度为100
                var h2 = this.mapHeight - h - this.obsDistance;
                var obs1 = new Obstacle(this.mapWidth, 0, h, this.obs);
                var obs2 = new Obstacle(this.mapWidth, this.mapHeight - h2, h2 - this.line, this.obs);
                this.obsList.push(obs1);
                this.obsList.push(obs2);
            }.bind(this);
        },
        CreateObs: function() {
		    // 随机产生障碍物上管道高度
            var h = Math.floor(Math.random() * (this.mapHeight - this.obsDistance - this.line));
            var h2 = this.mapHeight - h - this.obsDistance;
            var obs1 = new Obstacle(this.mapWidth, 0, h, this.obs);
            var obs2 = new Obstacle(this.mapWidth, this.mapHeight - h2, h2 - this.line, this.obs);
            this.obsList.push(obs1);
            this.obsList.push(obs2);

            // 移除越界障碍物  
            if (this.obsList[0].x < -this.obsList[0].width)
                this.obsList.splice(0, 2);
        },
        DrawObs: function() { //绘制障碍物 
            ctx.value.fillStyle = "#00ff00";
            for (var i = 0; i < this.obsList.length; i++) {
                this.obsList[i].x -= this.obsSpeed;
                if (i % 2)
                    this.obsList[i].draw(ctx.value, "up");
                else
                    this.obsList[i].draw(ctx.value, "down");
            }
        },
        CanMove: function() { 
            if (this.bird.y < 0 || this.bird.y > this.mapHeight - this.bird.height - this.line) {
			    this.gameOver = true;
            } else {
                var boundary = [{
                    x: this.bird.x,
                    y: this.bird.y
                }, {
                    x: this.bird.x + this.bird.width,
                    y: this.bird.y
                }, {
                    x: this.bird.x,
                    y: this.bird.y + this.bird.height
                }, {
                    x: this.bird.x + this.bird.width,
                    y: this.bird.y + this.bird.height
                }];
                for (var i = 0; i < this.obsList.length; i++) {
                    for (var j = 0; j < 4; j++)
                        if (boundary[j].x >= this.obsList[i].x && boundary[j].x <= this.obsList[i].x + this.obsList[i].width
                        && boundary[j].y >= this.obsList[i].y && boundary[j].y <= this.obsList[i].y + this.obsList[i].height)
                        {
                            this.gameOver = true;
                            break;
                        }
                    if (this.gameOver)
                        break;
                }
            }
        },
        ClearScreen: function() { // 清屏
            ctx.value.drawImage(this.bg, 0, 0);
        },
        CountScore: function() { // 计分
            if (this.obsList[0].x + this.obsList[0].width < this.startX &&this.obsList[0].flypast==false) {
                //小鸟坐标超过obsList[0]障碍物
                this.score += 1;
                this.obsList[0].flypast=true;			
            }
        },
        ShowScore: function() { // 显示分数  
            ctx.value.strokeStyle = "#000";
            ctx.value.lineWidth = 1;
            ctx.value.fillStyle = "#fff"
            ctx.value.fillText(this.score, 10, 50);
            ctx.value.strokeText(this.score, 10, 50);
        },
        CheckTouch: function() { // 检测触摸       
            if (this.touch) {
                this.bird.y -= this.upSpeed;
                this.bird.draw(ctx.value, "up");
            } else {
                this.bird.y += this.downSpeed;
                this.bird.draw(ctx.value, "down");
            }
        },
        ShowOver: function() {
            var overImg = new Image();
            overImg.src = overImage;
            overImg.onload = function(){
                ctx.value.drawImage(overImg, (this.mapWidth - overImg.width) / 2, (this.mapHeight - overImg.height) / 2 - 50);
            }.bind(this);

            return;
        }
    }

    

    onMounted(()=>{
        canvas.value = proxy.$refs.myCanvas;
        ctx.value = canvas.value.getContext('2d');

        game.value = new FlappyBird();
        
        initGame();

        canvas.value.onmousedown = function() {
            game.value.touch = true;
        }
        canvas.value.onmouseup = function() {
            game.value.touch = false;
        };
    });

    function initGame(){
        ctx.value.font = "3em 微软雅黑";
        game.value.CreateMap();
    }
    
    function RunGame(speed) {
        
        var updateTimer = setInterval(function() {
            // 若小鸟通过第一个障碍物启动记分器
            game.value.CanMove();
            if (game.value.gameOver) {
                game.value.ShowOver();
                clearInterval(updateTimer);
                return;
            }
            game.value.ClearScreen();
            game.value.DrawObs();
            game.value.CheckTouch();
            game.value.CountScore();
            game.value.ShowScore();
        }, speed);
        var obsTimer = setInterval(function() {
            if (game.value.gameOver) {
                clearInterval(obsTimer);
                return;
            }
            game.value.CreateObs();
        }, game.value.obsInterval);
    }

    function handleTouchStart(e){
        if (!IsPlay) {
            IsPlay = true;
            GameTime = RunGame(Speed);
        }
        if(game.value.gameOver) {
            location.reload();
        }
        game.value.touch = true;
        e.preventDefault(); 
    }

    function handleTouchEnd(e){
        game.value.touch = false;
        e.preventDefault();
    }

    function handleClick(){
        if (!IsPlay) {
            IsPlay = true;
            GameTime = RunGame(Speed);
        }
        if(game.value.gameOver) {
            location.reload();
        }
    }



</script>