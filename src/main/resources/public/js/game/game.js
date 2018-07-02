const WALL = -10;
const FIELD = -9;
const RIGHT = {x: 1, y: 0};
const LEFT = {x: -1, y: 0};
const DOWN = {x: 0, y: 1};
const UP = {x: 0, y: -1};
const map = {
    x: 13, y: 10, map:
        [[WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL],
            [WALL, FIELD, FIELD, WALL, FIELD, FIELD, FIELD, FIELD, FIELD, WALL, FIELD, FIELD, WALL],
            [WALL, FIELD, FIELD, WALL, FIELD, FIELD, FIELD, FIELD, FIELD, WALL, FIELD, FIELD, WALL],
            [WALL, FIELD, FIELD, WALL, FIELD, FIELD, FIELD, FIELD, FIELD, WALL, FIELD, FIELD, WALL],
            [WALL, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, WALL],
            [WALL, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, FIELD, WALL],
            [WALL, FIELD, FIELD, WALL, FIELD, FIELD, FIELD, FIELD, FIELD, WALL, FIELD, FIELD, WALL],
            [WALL, FIELD, FIELD, WALL, FIELD, FIELD, FIELD, FIELD, FIELD, WALL, FIELD, FIELD, WALL],
            [WALL, FIELD, FIELD, WALL, FIELD, FIELD, FIELD, FIELD, FIELD, WALL, FIELD, FIELD, WALL],
            [WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL]
        ],
    directions: [RIGHT, LEFT, DOWN, UP]
};
var enemy;
let gameState = function (game) {//
     };
gameState.prototype = {
    preload: function () {
        this.myTurn = false;
        this.awaitingMessage = this.game.add.text(map.x * 50 + 100, 300, 'Waiting for another player!', {font: '50px Arial'});
        this.whoseTurnText = this.game.add.text(map.x * 50 + 100, 350, '', {font: '50px Arial'});
        this.gameSocket = new WebSocket('ws://localhost:8080/game');
        this.gameSocket.onopen = bind(this.socketOnOpen, this);
        this.gameSocket.onmessage = bind(this.socketOnMessage, this);
        this.gameSocket.onerror = bind(this.socketOnError, this);
        this.gameSocket.onclose = bind(this.socketOnClose, this);

        this.pathMap = map.map.slice();
        this.fields = [];
        this.game.load.image('logo', 'img/game/phaser.png');
        this.game.load.image('ground', 'img/game/ground.png');
        this.game.load.image('wall', 'img/game/wall.png');
        this.game.load.image('hero', 'img/game/hero.png');
        this.game.load.image('update_button', 'img/game/update.png');
    },
    socketOnOpen: function () {
        console.log('GameSocket opened');
    },
    setWhoseTurn: function (bool) {
        this.myTurn = bool;
        if (this.myTurn) {
            this.whoseTurnText.text = 'Your turn!';
        } else {
            this.whoseTurnText.text = 'Enemy turn!';
        }
    },
    invertWhoseTurn: function () {
        this.setWhoseTurn(!this.myTurn);
    },
    socketOnMessage: function (event) {
        console.log('Got a message', event);
        let message = JSON.parse(event.data);
        console.log('Message', message);

        // what enemy did
        this.makeNumArrFromStrArr(message.x);
        this.makeNumArrFromStrArr(message.y);

        // this.enemyMoveTween.properties.x = message.x;
        // this.enemyMoveTween.properties.y = message.y;
        this.addArrayAtBeginning(message.x, this.enemyMoveTween.properties.x);
        this.addArrayAtBeginning(message.y, this.enemyMoveTween.properties.y);
        debugger;
        this.enemyMoveTween.start();
        // this.enemy.movePoint.x = message.x;
        // this.enemy.movePoint.y = message.y;
    },
    socketOnClose: function (event) {
        console.log('Socket closed!', event);
    },
    socketOnError: function (event) {
        console.log("Error with socket!", event);
    },
    create: function () {
        this.game.stage.backgroundColor = '#a8aa33';
        this.ground = this.game.add.group();
        this.walls = this.game.add.group();
        this.stepCount = 0;

        let sprite;
        for (let i = 0; i < map.x; i++) {
            for (let j = 0; j < map.y; j++) {
                if (map.map[j][i] === WALL) {
                    sprite = this.game.add.sprite(50 * i, 50 * j, 'wall');
                    sprite.posX = i;
                    sprite.posY = j;

                    this.walls.add(sprite);
                } else {
                    sprite = this.game.add.sprite(50 * i, 50 * j, 'ground');
                    sprite.posX = i;
                    sprite.posY = j;

                    this.ground.add(sprite);
                }
                this.fields.push(sprite);
            }
        }
        this.hero = this.game.add.sprite(50 * 2 + 25, 50 * 5 + 25, 'hero');
        this.hero.anchor.x = 0.5;
        this.hero.anchor.y = 0.5;
        this.hero.posX = 2;
        this.hero.posY = 5;

        this.enemy = this.game.add.sprite(50 * (map.x - 2) + 25, 50 * 5 + 25, 'hero');
        this.enemy.anchor.x = 0.5;
        this.enemy.anchor.y = 0.5;
        this.enemy.posX = map.x - 2;
        this.enemy.posY = 5;
        // this.enemy.movePoint.x = [];

        enemy = this.enemy;

        // debugger;

        let yourTurnFirst = JSON.parse(sessionStorage.getItem('yourTurnFirst'));
        console.log(yourTurnFirst);
        if (yourTurnFirst) {
            let tmp = this.enemy;
            this.enemy = this.hero;
            this.hero = tmp;
        }
        this.setWhoseTurn(yourTurnFirst);
        this.awaitingMessage.text = 'Your enemy: ' + sessionStorage.getItem('enemy');


        this.ground.setAll('inputEnabled', true);
        this.ground.callAll('events.onInputOver.add', 'events.onInputOver', bind(this.highLightPath, this));
        this.ground.callAll('events.onInputOver.add', 'events.onInputOut', bind(this.lowLightPath, this));

        this.ground.callAll('events.onInputDown.add', 'events.onInputDown', bind(this.moveHero, this));

        this.stepButton = this.game.add.button(map.x * 50 + 10, 60, 'update_button', this.addSteps, this);
        this.stepCountText = this.game.add.text(map.x * 50 + 10, 100, this.stepCount, {font: '20px Arial'});

        this.heroMoveTween = this.game.add.tween(this.hero).to({}, 2000, null, false);
        this.heroMoveTween.onComplete.add(this.clearMovePoints, this.heroMoveTween);
        this.heroMoveTween.properties.x = [];
        this.heroMoveTween.properties.y = [];

        this.enemyMoveTween = this.game.add.tween(this.enemy).to({}, 2000, null, false);
        this.enemyMoveTween.onComplete.add(this.clearMovePoints, this.enemyMoveTween);
        this.enemyMoveTween.onComplete.add(this.invertWhoseTurn, this);
        this.enemyMoveTween.properties.x = [];
        this.enemyMoveTween.properties.y = [];
    },
    highLightPath: function (event) {
        // debugger;
        if (this.myTurn && this.canStepToField(event.posX, event.posY, this.pathMap)) {
            this.markPath(event.posX, event.posY, 0.65, this.pathMap);
        }
    },
    lowLightPath: function (event) {
        // debugger;
        if (this.myTurn) {
            this.markPath(event.posX, event.posY, 1, this.pathMap);
        }
    },
    clearMovePoints: function () {
        console.log(this);
        this.properties.x.length = 0;
        this.properties.y.length = 0;
    },
    moveHero: function (event) {
        if (this.myTurn) {
            console.log('Click');

            if (this.stepCount > 0 && this.canStepToField(event.posX, event.posY, this.pathMap)) {
                this.stepCount = this.stepCount - this.pathMap[event.posY][event.posX];
                this.stepCountText.text = this.stepCount.toString();
                this.markPath(event.posX, event.posY, 1, this.pathMap);
                let movement = this.moveHeroByPath(this.hero, event.posX, event.posY, this.pathMap);
                // sending our movement to opponent
                this.gameSocket.send(JSON.stringify(movement));
                this.invertWhoseTurn();

                this.hero.posX = event.posX;
                this.hero.posY = event.posY;

                this.pathMap = this.slice2DimensionalArray(map.map);
                this.markFieldsAroundHero(this.hero, this.stepCount, this.pathMap);
            }
        }
    },
    moveHeroByPath: function (hero, destX, destY, mapPath) {
        while (destX !== hero.posX || destY !== hero.posY) {
            for (let i = 0; i < map.directions.length; ++i) {
                if (mapPath[destY + map.directions[i].y][destX + map.directions[i].x] === (mapPath[destY][destX] - 1)) {
                    this.heroMoveTween.properties.x.unshift(destX * 50 + 25);
                    this.heroMoveTween.properties.y.unshift(destY * 50 + 25);
                    destX += map.directions[i].x;
                    destY += map.directions[i].y;
                    break;
                }
            }
        }
        this.heroMoveTween.properties.x.unshift(destX * 50 + 25);
        this.heroMoveTween.properties.y.unshift(destY * 50 + 25);
        debugger;
        this.heroMoveTween.start();
        return {x: this.heroMoveTween.properties.x, y: this.heroMoveTween.properties.y};
    },
    addSteps: function () {
        if (this.myTurn) {
            this.stepCount = 5;
            this.stepCountText.text = this.stepCount.toString();
            this.pathMap = this.slice2DimensionalArray(map.map);
            this.markFieldsAroundHero(this.hero, this.stepCount, this.pathMap);
            console.log(this.pathMap);
        }
    },
    markFieldsAroundHero: function (hero, stepCount, mapPath) {
        mapPath[hero.posY][hero.posX] = 0;
        if (stepCount < 1) return;
        let queue = [];
        let depth = 1;
        const MARKER = {};
        queue.push({x: hero.posX, y: hero.posY}, MARKER);
        while (queue.length !== 0) {
            let curPos = queue.shift();
            if (curPos === MARKER) {
                depth++;
                if (depth > stepCount) break;
                queue.push(MARKER);
            } else {
                for (let i = 0; i < map.directions.length; ++i) {
                    if (mapPath[curPos.y + map.directions[i].y][curPos.x + map.directions[i].x] === FIELD) {
                        mapPath[curPos.y + map.directions[i].y][curPos.x + map.directions[i].x] = depth;
                        queue.push({x: curPos.x + map.directions[i].x, y: curPos.y + map.directions[i].y})
                    }
                }
            }
        }
    },
    markPath: function (destX, destY, alpha, mapPath) {
        if (mapPath[destY][destX] <= 0) return;
        this.fields[destX * map.y + destY].alpha = alpha;
        while (mapPath[destY][destX] !== 0) {
            for (let i = 0; i < map.directions.length; ++i) {
                if (mapPath[destY + map.directions[i].y][destX + map.directions[i].x] === (mapPath[destY][destX] - 1)) {
                    destX += map.directions[i].x;
                    destY += map.directions[i].y;
                    break;
                }
            }
            this.fields[destX * map.y + destY].alpha = alpha;
        }
    },
    canStepToField: function (destX, destY, mapPath) {
        return mapPath[destY][destX] > 0;
    },
    slice2DimensionalArray: function (arr) {
        let newArr = [];
        arr.forEach(function (value) {
            newArr.push(value.slice());
        });
        return newArr;
    },
    update: function () {
        // if(isNaN(this.enemy.x) || isNaN(this.enemy.y)) {
        //     this.enemy.x = this.enemy.lastX;
        //     this.enemy.y = this.enemy.lastY;
        // }
        // if(isNaN(this.hero.x) || isNaN(this.hero.y)){
        //     this.hero.x = this.hero.lastX;
        //     this.hero.y = this.hero.lastY;
        // }
        // this.enemy.lastX = this.enemy.x;
        // this.enemy.lastY = this.enemy.y;
        //
        // this.hero.lastX = this.hero.x;
        // this.hero.lastY = this.hero.y;
    },
    makeNumArrFromStrArr: function (arr) {
        for(let i=0;i<arr.length; ++i) {
            arr[i] = Number.parseInt(arr[i]);
        }
    },
    addArrayAtBeginning(arr, result) {
        for(let i=arr.length - 1; i >= 0; --i) {
            result.unshift(arr[i]);
        }
    }

};
window.onload = function () {
    let game = new Phaser.Game('100%', '100%', Phaser.AUTO, '#center');
    game.state.add('Queue', queueState);
    game.state.add('Game', gameState);
    game.state.start('Queue');

};