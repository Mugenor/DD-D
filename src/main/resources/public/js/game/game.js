let bind = function (func, context) {
    return function () {
        func.apply(context, arguments);
    }
};
const WALL = -10;
const FIELD1 = -9;
const FIELD2 = -8;
const FIELD3 = -7;
const CELL_SIZE = 64;
const HALF_CELL_SIZE = CELL_SIZE / 2;
const RIGHT = {x: 1, y: 0};
const LEFT = {x: -1, y: 0};
const DOWN = {x: 0, y: 1};
const UP = {x: 0, y: -1};
const ENEMY_TURN_STATE = 0;
const THROW_CUBE_STATE = 1;
const WALK_STATE = 2;
const TAKE_CARD_STATE = 3;
const CARD_STATE = 4;
const map = {
    x: 13, y: 10, map:
        [[WALL, WALL, WALL, WALL, WALL, FIELD2, FIELD1, FIELD2, WALL, WALL, WALL, WALL, WALL],
            [WALL, FIELD1, FIELD2, WALL, FIELD2, FIELD1, FIELD2, FIELD1, FIELD2, WALL, FIELD2, FIELD1, WALL],
            [WALL, FIELD2, FIELD1, WALL, FIELD1, FIELD2, FIELD1, FIELD2, FIELD1, WALL, FIELD1, FIELD2, WALL],
            [WALL, FIELD1, FIELD2, WALL, FIELD2, WALL, FIELD2, FIELD1, FIELD2, WALL, FIELD2, FIELD1, WALL],
            [WALL, FIELD2, FIELD1, FIELD2, FIELD1, WALL, FIELD1, FIELD2, FIELD1, FIELD2, FIELD1, FIELD2, WALL],
            [WALL, FIELD1, FIELD2, FIELD1, FIELD2, FIELD1, FIELD2, FIELD1, FIELD2, FIELD1, FIELD2, FIELD1, WALL],
            [WALL, FIELD2, FIELD1, WALL, FIELD1, FIELD2, FIELD1, FIELD2, FIELD1, WALL, FIELD1, FIELD2, WALL],
            [WALL, FIELD1, FIELD2, WALL, FIELD2, FIELD1, WALL, WALL, FIELD2, WALL, FIELD2, FIELD1, WALL],
            [WALL, FIELD2, FIELD1, WALL, FIELD1, FIELD2, FIELD1, FIELD2, FIELD1, WALL, FIELD1, FIELD2, WALL],
            [WALL, WALL, WALL, WALL, WALL, FIELD1, FIELD2, FIELD1, WALL, WALL, WALL, WALL, WALL]
        ],
    directions: [RIGHT, LEFT, DOWN, UP]
};
var enemy;
let gameState = function (game) {//
};

gameState.prototype = {
    preload: function () {
        this.myTurn = false;

        gameSocket.onopen = bind(this.socketOnOpen, this);
        gameSocket.onmessage = bind(this.socketOnMessage, this);
        gameSocket.onerror = bind(this.socketOnError, this);
        gameSocket.onclose = bind(this.socketOnClose, this);

        this.pathMap = map.map.slice();
        this.fields = [];
        this.game.load.image('logo', 'img/game/phaser.png');
        this.game.load.image('field1', '/img/game/field1.jpg');
        this.game.load.image('field2', '/img/game/field3.jpg');
        this.game.load.image('wall', 'img/game/field5.jpg');
        this.game.load.image('player', this.playerHero.gameImage);
        this.game.load.image('enemy', this.enemyHero.gameImage);
        this.game.load.image('update_button', 'img/game/update.png');
        let th = this;
        $.ajax('/card', {
            method: "GET",
            contentType: 'application/json',
            async: false,
            success: function (resp) {
                th.cards = resp;
            }
        });
    },
    socketOnOpen: function () {
        console.log('GameSocket opened');
    },
    setWhoseTurn: function (bool) {
        this.myTurn = bool;
        if (this.myTurn) {
            this.state = THROW_CUBE_STATE;
            this.whoseTurnText.html('Теперь твой ход!');
        } else {
            this.state = ENEMY_TURN_STATE;
            this.whoseTurnText.html('Ход противника!');
        }
    },
    invertWhoseTurn: function () {
        this.setWhoseTurn(!this.myTurn);
    },
    socketOnMessage: function (event) {
        console.log('Got a message', event);
        let message = JSON.parse(event.data);
        console.log('Message', message);
        for(let i=0; i < message.length; ++i) {
            // what enemy did
            if(message[i].movement) {
                this.makeNumArrFromStrArr(message[i].movement.x);
                this.makeNumArrFromStrArr(message[i].movement.y);

                this.addArrayAtBeginning(message[i].movement.x, this.enemyMoveTween.properties.x);
                this.addArrayAtBeginning(message[i].movement.y, this.enemyMoveTween.properties.y);
                // debugger;
                this.enemy.posX = (message[i].movement.x[message[i].movement.x.length - 1] - HALF_CELL_SIZE) / CELL_SIZE;
                this.enemy.posY = (message[i].movement.y[message[i].movement.y.length - 1] - HALF_CELL_SIZE) / CELL_SIZE;
                console.log(this.enemy);
            }


            if(message[i].card) {
                this.applyCard(message[i].card);
            }
        }
        this.enemyMoveTween.start();
        this.markFieldsAroundHero(this.hero, this.stepCount, this.pathMap);
    },
    socketOnClose: function (event) {
        console.log('Socket closed!', event);
    },
    socketOnError: function (event) {
        console.log("Error with socket!", event);
    },
    create: function () {
        this.game.stage.backgroundColor = '#defa5b';
        this.ground = this.game.add.group();
        this.walls = this.game.add.group();
        this.stepCount = 0;
        this.gameDiv = $('#center');

        let sprite;
        for (let i = 0; i < map.x; i++) {
            for (let j = 0; j < map.y; j++) {
                if (map.map[j][i] === WALL) {
                    sprite = this.game.add.sprite(CELL_SIZE * i, CELL_SIZE * j, 'wall');
                    sprite.posX = i;
                    sprite.posY = j;

                    this.walls.add(sprite);
                } else if (map.map[j][i] === FIELD1) {
                    sprite = this.game.add.sprite(CELL_SIZE * i, CELL_SIZE * j, 'field1');
                    sprite.posX = i;
                    sprite.posY = j;

                    this.ground.add(sprite);
                } else if (map.map[j][i] === FIELD2) {
                    sprite = this.game.add.sprite(CELL_SIZE * i, CELL_SIZE * j, 'field2');
                    sprite.posX = i;
                    sprite.posY = j;

                    this.ground.add(sprite);
                }
                sprite.scale.set(CELL_SIZE / sprite.texture.width, CELL_SIZE / sprite.texture.height);
                this.fields.push(sprite);
            }
        }
        let yourTurnFirst = JSON.parse(sessionStorage.getItem('yourTurnFirst'));
        console.log(yourTurnFirst);
        let plX = 6, plY = 0, enX = 6, enY = 9, plSprite = 'player', enSprite = 'enemy';
        if (!yourTurnFirst) {
            let tmp = plX;
            plX = enX;
            enX = tmp;

            tmp = plY;
            plY = enY;
            enY = tmp;
        }

        this.hero = this.game.add.sprite(CELL_SIZE * plX + HALF_CELL_SIZE, CELL_SIZE * plY + HALF_CELL_SIZE, plSprite);
        this.hero.anchor.x = 0.5;
        this.hero.anchor.y = 0.5;
        this.hero.posX = plX;
        this.hero.posY = plY;
        this.hero.scale.set(CELL_SIZE / this.hero.texture.width / 2, CELL_SIZE / this.hero.texture.height);

        this.enemy = this.game.add.sprite(CELL_SIZE * enX + HALF_CELL_SIZE, CELL_SIZE * enY + HALF_CELL_SIZE, enSprite);
        this.enemy.anchor.x = 0.5;
        this.enemy.anchor.y = 0.5;
        this.enemy.posX = enX;
        this.enemy.posY = enY;
        this.enemy.scale.set(CELL_SIZE / this.enemy.texture.width / 2, CELL_SIZE / this.enemy.texture.height);


        this.ground.setAll('inputEnabled', true);
        this.ground.callAll('events.onInputOver.add', 'events.onInputOver', bind(this.highLightPath, this));
        this.ground.callAll('events.onInputOver.add', 'events.onInputOut', bind(this.lowLightPath, this));

        this.ground.callAll('events.onInputDown.add', 'events.onInputDown', bind(this.moveHero, this));


        this.refuseCardButton = $('<button/>', {
            id: 'refuse',
            class: 'game_button margin_button'
        }).html("Сбросить").click(bind(this.refuseCard, this)).prependTo(this.gameDiv);
        this.applyCardButton = $('<button/>', {
            id: 'apply',
            class: 'game_button margin_button'
        }).html("Применить").click(bind(this.useCard, this)).prependTo(this.gameDiv);
        $('<br>').prependTo(this.gameDiv);
        this.cardText = $('<span/>', {
            id: 'cardText'
        }).prependTo(this.gameDiv);
        this.takeCardButton = $('<button/>', {
            id: 'cardsButton',
            class: 'game_button margin_button'
        }).html("Получить карточку").click(bind(this.addCard, this)).prependTo(this.gameDiv);
        $('<br>').prependTo(this.gameDiv);
        this.whoseTurnText = $('<span/>', {
            id: 'whoseTurnText'
        }).prependTo(this.gameDiv);
        this.setWhoseTurn(yourTurnFirst);
        this.stepCountText = $('<span/>', {
            id: 'stepCountText'
        }).prependTo(this.gameDiv);
        this.throwCubeButton = $('<button/>', {
            id: 'stepsButton',
            class: 'game_button margin_button'
        }).html("Получить шаги").click(bind(this.addSteps, this)).prependTo(this.gameDiv);

        this.awaitingMessage = $('<p/>', {
            id: 'awaitingMessage'
        }).html('Твой противник: ' + sessionStorage.getItem('enemy')).prependTo(this.gameDiv);

        this.heroMoveTween = this.game.add.tween(this.hero).to({}, 2000, null, false);
        this.heroMoveTween.onComplete.add(this.clearMovePoints, this.heroMoveTween);
        this.heroMoveTween.properties.x = [];
        this.heroMoveTween.properties.y = [];

        this.enemyMoveTween = this.game.add.tween(this.enemy).to({}, 2000, null, false);
        this.enemyMoveTween.onComplete.add(this.clearMovePoints, this.enemyMoveTween);
        this.enemyMoveTween.onComplete.add(this.invertWhoseTurn, this);
        this.enemyMoveTween.properties.x = [];
        this.enemyMoveTween.properties.y = [];

        this.queue = [];

        // this.playerHealthBar = $
    },
    highLightPath: function (event) {
        // debugger;
        // console.log('HighLight: ', event);
        if (this.state === WALK_STATE && this.myTurn && this.canStepToField(event.posX, event.posY, this.pathMap)) {
            this.markPath(event.posX, event.posY, 0.65, this.pathMap);
        }
    },
    lowLightPath: function (event) {
        // debugger;
        // console.log('LowLight: ', event);
        if (this.myTurn && this.state === WALK_STATE) {
            this.markPath(event.posX, event.posY, 1, this.pathMap);
        }
    },
    clearMovePoints: function () {
        console.log(this);
        this.properties.x.length = 0;
        this.properties.y.length = 0;
    },
    moveHero: function (event) {
        if (this.myTurn && this.state === WALK_STATE) {
            console.log('Click');

            if (this.stepCount > 0 && this.canStepToField(event.posX, event.posY, this.pathMap)) {
                this.stepCount = this.stepCount - this.pathMap[event.posY][event.posX];
                this.stepCountText.html(this.stepCount.toString());
                this.markPath(event.posX, event.posY, 1, this.pathMap);
                let movement = this.moveHeroByPath(this.hero, event.posX, event.posY, this.pathMap);
                this.queue.push({movement: movement});

                this.hero.posX = event.posX;
                this.hero.posY = event.posY;

                this.pathMap = this.slice2DimensionalArray(map.map);
                this.state = TAKE_CARD_STATE;
                this.takeCardButton.prop('disabled', false);
            }
        }
    },
    moveHeroByPath: function (hero, destX, destY, mapPath) {
        while (destX !== hero.posX || destY !== hero.posY) {
            for (let i = 0; i < map.directions.length; ++i) {
                let newY = destY + map.directions[i].y;
                let newX = destX + map.directions[i].x;
                if (newY >= 0 && newX >= 0 && newY < map.y && newX < map.x &&
                    mapPath[newY][newX] === (mapPath[destY][destX] - 1)) {
                    this.heroMoveTween.properties.x.unshift(destX * CELL_SIZE + HALF_CELL_SIZE);
                    this.heroMoveTween.properties.y.unshift(destY * CELL_SIZE + HALF_CELL_SIZE);
                    destX += map.directions[i].x;
                    destY += map.directions[i].y;
                    break;
                }
            }
        }
        this.heroMoveTween.properties.x.unshift(destX * CELL_SIZE + HALF_CELL_SIZE);
        this.heroMoveTween.properties.y.unshift(destY * CELL_SIZE + HALF_CELL_SIZE);
        this.heroMoveTween.start();
        return {x: this.heroMoveTween.properties.x, y: this.heroMoveTween.properties.y};
    },
    randomInteger: function (min, max) {
        let rand = min - 0.5 + Math.random() * (max - min + 1);
        rand = Math.round(rand);
        return rand;
    },
    addSteps: function () {
        console.log('In add steps');
        if (this.myTurn && this.state === THROW_CUBE_STATE) {
            this.stepCount = this.randomInteger(1, 6);
            this.stepCountText.html(this.stepCount.toString());
            this.pathMap = this.slice2DimensionalArray(map.map);
            this.markFieldsAroundHero(this.hero, this.stepCount, this.pathMap);
            this.state = WALK_STATE;
            this.throwCubeButton.prop('disabled', true);
            console.log(this.pathMap);
        }
    },
    addCard: function () {
        console.log('In add card');
        if (this.myTurn && this.state === TAKE_CARD_STATE) {
            this.cardNumber = this.randomInteger(0,37);
            let card = this.cards[this.cardNumber];
            this.cardText.html(card.cubeNumber + ' - '+ card.name + ' - ' + card.action);

            this.takeCardButton.prop('disabled', true);

            this.applyCardButton.prop('disabled', false);
            this.refuseCardButton.prop('disabled', false);

            this.state = CARD_STATE;

            console.log(this.cards[this.cardNumber - 1]);
        }
    },
    useCard: function() {
        console.log('Пора достать карточку из БД и применить');
        if(this.myTurn && this.state === CARD_STATE) {
            // TODO применить урон, здоровье, лог, вывести всё это для игрока
            let card = this.cards[this.cardNumber];
            this.applyCard(card);
            this.queue[this.queue.length - 1].card = card;
            if(card.gamer) {
                // TODO ход остаётся у игрока
            } else {
                // TODO ход передаётся противнику
            }
        }
    },
    applyCard: function(card) {
        let playerHealth = Number.parseInt(playerHealthEl.html());
        let enemyHealth = Number.parseInt(enemyHealthEl.html());
        playerHealth += card.health;
        enemyHealth += card.damage;
    },
    refuseCard: function() {
        console.log('Ход переходит другому игроку');
        if(this.myTurn && this.state === CARD_STATE) {
            this.invertWhoseTurn();
            gameSocket.send(JSON.stringify(this.queue));
            this.queue = [];
            this.state = ENEMY_TURN_STATE;
        }
    },
    markFieldsAroundHero: function (hero, stepCount, mapPath) {
        mapPath[hero.posY][hero.posX] = 0;
        if (stepCount < 1) return;
        let queue = [];
        let depth = 1;
        const MARKER = {};
        queue.push({x: hero.posX, y: hero.posY}, MARKER);
        debugger;
        while (queue.length !== 0) {
            let curPos = queue.shift();
            if (curPos === MARKER) {
                depth++;
                if (depth > stepCount) break;
                queue.push(MARKER);
            } else {
                for (let i = 0; i < map.directions.length; ++i) {
                    let newY = curPos.y + map.directions[i].y;
                    let newX = curPos.x + map.directions[i].x;
                    if (newY >= 0 && newX >= 0 && newY < map.y && newX < map.x &&
                        (mapPath[newY][newX] === FIELD1 ||
                            mapPath[newY][newX] === FIELD2)
                        && !(newX === this.enemy.posX && newY === this.enemy.posY)) {
                        mapPath[newY][newX] = depth;
                        queue.push({x: newX, y: newY})
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
                let newY = destY + map.directions[i].y;
                let newX = destX + map.directions[i].x;
                if (newY >= 0 && newX >= 0 && newY < map.y && newX < map.x &&
                    mapPath[newY][newX] === (mapPath[destY][destX] - 1)) {
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
    },
    makeNumArrFromStrArr: function (arr) {
        for (let i = 0; i < arr.length; ++i) {
            arr[i] = Number.parseInt(arr[i]);
        }
    },
    addArrayAtBeginning(arr, result) {
        for (let i = arr.length - 1; i >= 0; --i) {
            result.unshift(arr[i]);
        }
    }

};
// window.onload = function () {
//     let width = CELL_SIZE * map.x;
//     let height = CELL_SIZE * map.y;
//     let game = new Phaser.Game(width, height, Phaser.AUTO, 'center');
//     // game.state.add('Queue', queueState);
//     game.state.add('Game', gameState);
//     game.state.start('Queue');
//
// };