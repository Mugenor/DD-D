$(function () {
    let queueButton = $('#queueButton');
    let center = $('#center');
    queueButton.click(function (event) {
        center.html('Подождите немного, мы ищем Вам соперника...');
        let queueSocket = new WebSocket('ws://localhost:8080/queue');
        queueSocket.onmessage = function (event) {
            let message = JSON.parse(event.data);
            sessionStorage.setItem('enemy', message.enemy);
            sessionStorage.setItem('yourTurnFirst', message.yourTurnFirst);
            queueSocket.close();
            queueButton.remove();
            chooseHero();

        }
    });
});
// queueState.prototype = {
//     preload: function () {
//         this.game.load.image('queueButton', 'img/game/joinButton.png');
//         this.isSocketOpened = false;
//     },
//     create: function () {
//         // Create interface which shows process of queuing
//         this.game.stage.backgroundColor = '#4440aa';
//         this.stateMessage = this.game.add.text(30, 170, 'State message', {font: '20px Arial'});
//         this.enemyName = this.game.add.text(30, 200, 'Enemy name', {font: '20px Arial'});
//         this.entryQueueButton = this.game.add.button(30, 30, 'queueButton', this.enterTheQueue, this);
//     },
//     update: function () {
//
//     },
//     shutdown: function () {
//         // Close Socket
//         if(this.isSocketOpened){
//             this.queueSocket.close();
//         }
//     },
//     enterTheQueue: function () {
//         if (!this.isSocketOpened) {
//             this.queueSocket = new WebSocket('ws://localhost:8080/queue');
//             this.queueSocket.onopen = bind(this.socketOnOpen, this);
//             this.queueSocket.onmessage = bind(this.socketOnMessage, this);
//             this.queueSocket.onclose = bind(this.socketOnClose, this);
//             this.queueSocket.onerror = bind(this.socketOnError, this);
//         }
//     },
//     socketOnOpen: function () {
//         this.stateMessage.text = 'Socket opened';
//         this.isSocketOpened = true;
//     },
//     socketOnMessage: function (event) {
//         let message = JSON.parse(event.data);
//         sessionStorage.setItem('enemy', message.enemy);
//         sessionStorage.setItem('yourTurnFirst', message.yourTurnFirst);
//         this.enemyName.text = 'Enemy name: ' + message.enemy;
//         this.game.state.start('Game');
//     },
//     socketOnClose: function (event) {
//         this.stateMessage.text = 'Socket closed';
//         this.isSocketOpened = false;
//     },
//     socketOnError: function (event) {
//     }
// };
