let queueState = function (game) {};
let bind = function (func, context){
    return function(){
        func.apply(context, arguments);
    }
};
queueState.prototype = {
    preload: function () {
        this.game.load.image('queueButton', 'img/game/joinButton.png');
        this.isSocketOpened = false;
    },
    create: function () {
        // Create interface which shows process of queuing
        this.game.stage.backgroundColor = '#4440aa';
        this.stateMessage = this.game.add.text(30, 170, 'State message', {font: '20px Arial'});
        this.enemyName = this.game.add.text(30, 200, 'Enemy name', {font: '20px Arial'});
        console.log(this.stateMessage, this.enemyName, this.game);
        this.entryQueueButton = this.game.add.button(30, 30, 'queueButton', this.enterTheQueue, this);
    },
    update: function () {

    },
    shutdown: function () {
        // Close Socket
        if(this.isSocketOpened){
            this.queueSocket.close();
        }
    },
    enterTheQueue: function () {
        if (!this.isSocketOpened) {
            this.queueSocket = new WebSocket('ws://localhost:8080/queue');
            this.queueSocket.onopen = bind(this.socketOnOpen, this);
            this.queueSocket.onmessage = bind(this.socketOnMessage, this);
            this.queueSocket.onclose = bind(this.socketOnClose, this);
            this.queueSocket.onerror = bind(this.socketOnError, this);
        }
    },
    socketOnOpen: function () {
        console.log('Socket opened');
        this.stateMessage.text = 'Socket opened';
        this.isSocketOpened = true;
    },
    socketOnMessage: function (event) {
        console.log('Got a message: ', event);
        let message = JSON.parse(event.data);
        sessionStorage.setItem('enemy', message.enemy);
        sessionStorage.setItem('yourTurnFirst', message.yourTurnFirst);
        this.enemyName.text = 'Enemy name: ' + message.enemy;
        this.game.state.start('Game');
    },
    socketOnClose: function (event) {
        console.log('Socket closed', event);
        this.stateMessage.text = 'Socket closed';
        this.isSocketOpened = false;
    },
    socketOnError: function (event) {
        console.log('ERROR WITH SOCKET!', event);
    }
};