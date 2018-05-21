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
            [WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL, WALL],
        ],
    directions: [RIGHT, LEFT, DOWN, UP]
};
let pathMap;
let ground;
let walls;
let fields;
let hero;
let stepButton;
let stepCountText;
let stepCount = 0;

let heroMoveTween;
window.onload = function () {
    pathMap = map.map.slice();
    fields = [];

    let game = new Phaser.Game('100%', '100%', Phaser.AUTO, document.body, {
        preload: preload,
        create: create,
        update: update
    });

    function preload() {
        game.load.image('logo', 'img/phaser.png');
        game.load.image('ground', 'img/ground.png');
        game.load.image('wall', 'img/wall.png');
        game.load.image('hero', 'img/hero.png');
        game.load.image('update_button', 'img/update.png');
    }

    function create() {
        game.stage.backgroundColor = "#a8aa33";
        ground = game.add.group();
        walls = game.add.group();
        stepCount = 0;

        let sprite;
        for (let i = 0; i < map.x; i++) {
            for (let j = 0; j < map.y; j++) {
                if (map.map[j][i] === WALL) {
                    sprite = game.add.sprite(50 * i, 50 * j, 'wall');
                    sprite.posX = i;
                    sprite.posY = j;

                    walls.add(sprite);
                } else {
                    sprite = game.add.sprite(50 * i, 50 * j, 'ground');
                    sprite.posX = i;
                    sprite.posY = j;

                    ground.add(sprite);
                }
                fields.push(sprite);
            }
        }
        hero = game.add.sprite(50 * 6 + 25, 50 * 5 + 25, 'hero');
        hero.anchor.x = 0.5;
        hero.anchor.y = 0.5;
        hero.posX = 6;
        hero.posY = 5;


        ground.setAll('inputEnabled', true);
        ground.callAll('events.onInputOver.add', 'events.onInputOver', function (event) {
            if (canStepToField(event.posX, event.posY, pathMap)) {
                markPath(event.posX, event.posY, 0.65, pathMap);
            }
        });
        ground.callAll('events.onInputOver.add', 'events.onInputOut', function (event) {
            markPath(event.posX, event.posY, 1, pathMap);
        });

        ground.callAll('events.onInputDown.add', 'events.onInputDown', function (event) {
            console.log('Click');
            if (stepCount > 0 && canStepToField(event.posX, event.posY, pathMap)) {
                stepCount = stepCount - pathMap[event.posY][event.posX];
                stepCountText.text = stepCount.toString();
                markPath(event.posX, event.posY, 1, pathMap);
                moveHeroByPath(hero, event.posX, event.posY, pathMap);
                hero.posX = event.posX;
                hero.posY = event.posY;

                pathMap = slice2DimensionalArray(map.map);
                markFieldsAroundHero(hero, stepCount, pathMap);
            }
        });

        stepButton = game.add.button(map.x * 50 + 10, 60, 'update_button', addSteps, this);
        stepCountText = game.add.text(map.x * 50 + 10, 100, stepCount, {font: '20px Arial'});

        heroMoveTween = game.add.tween(hero).to({}, 2000, null, false);
        heroMoveTween.onComplete.add(clearMovePoints, this);
        heroMoveTween.properties.x = [];
        heroMoveTween.properties.y = [];
    }

    function clearMovePoints() {
        heroMoveTween.properties.x.length = 0;
        heroMoveTween.properties.y.length = 0;
        console.log(heroMoveTween);
    }

    function moveHeroByPath(hero, destX, destY, mapPath) {
        while (destX !== hero.posX || destY !== hero.posY) {
            for (let i = 0; i < map.directions.length; ++i) {
                if (mapPath[destY + map.directions[i].y][destX + map.directions[i].x] === (mapPath[destY][destX] - 1)) {
                    heroMoveTween.properties.x.unshift(destX * 50 + 25);
                    heroMoveTween.properties.y.unshift(destY * 50 + 25);
                    destX += map.directions[i].x;
                    destY += map.directions[i].y;
                    break;
                }
            }
        }
        heroMoveTween.properties.x.unshift(destX * 50 + 25);
        heroMoveTween.properties.y.unshift(destY * 50 + 25);
        heroMoveTween.start();
    }

    function addSteps() {
        stepCount = 5;
        stepCountText.text = stepCount.toString();
        pathMap = slice2DimensionalArray(map.map);
        markFieldsAroundHero(hero, stepCount, pathMap);
    }

    function markFieldsAroundHero(hero, stepCount, mapPath) {
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
    }

    function canStepToField(destX, destY, mapPath) {
        return mapPath[destY][destX] > 0;
    }

    function markPath(destX, destY, alpha, mapPath) {
        if (mapPath[destY][destX] <= 0) return;
        fields[destX * map.y + destY].alpha = alpha;
        while (mapPath[destY][destX] !== 0) {
            for (let i = 0; i < map.directions.length; ++i) {
                if (mapPath[destY + map.directions[i].y][destX + map.directions[i].x] === (mapPath[destY][destX] - 1)) {
                    destX += map.directions[i].x;
                    destY += map.directions[i].y;
                    break;
                }
            }
            fields[destX * map.y + destY].alpha = alpha;
        }
    }

    function slice2DimensionalArray(arr) {
        let newArr = [];
        arr.forEach(function (value) {
            newArr.push(value.slice());
        });
        return newArr;
    }

    function update() {
    }
};