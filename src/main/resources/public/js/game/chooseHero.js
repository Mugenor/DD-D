let gameSocket;
function chooseHero() {
    let enemyHero;
    let playerHero;
    let isChosed = false;
    gameSocket = new WebSocket('ws://localhost:8080/game');
    gameSocket.onopen = function() {
        console.log('Game socket opened');
    };
    gameSocket.onmessage = function(event) {
        enemyHero = JSON.parse(event.data);
        if(enemyHero && playerHero && isChosed) {
            startGame(playerHero, enemyHero);
        }
    };

    window.template = function (id) {
        return _.template($('#' + id).html());
    };

    let Hero = Backbone.Model.extend({

    });

    let HeroView = Backbone.View.extend({
        className: 'character',
        template: template('characters'),
        initialize: function() {
            this.render();
        },
        render: function() {
            debugger;
            this.$el.html(this.template(this.model.toJSON()));
            this.$el.prop('id', this.model.get('id') - 1);
            return this;
        }
    });

    let HeroesCollection = Backbone.Collection.extend({
        url: '/character',
        model: Hero
    });

    let HeroesView = Backbone.View.extend({
        id: 'all_characters',
        initialize: function() {
            this.collection.fetch({
                context: this,
                success: this.render,
                error: this.onError
            })
        },
        render: function() {
            this.collection.each(this.addOne, this);
            let th = this;
            let heroesCards = $('.character');
            heroesCards.click(function (event) {
                if(!isChosed) {
                    console.log(this, event);
                    heroesCards.removeClass('.chosen');
                    playerHero = th.collection.models[this.id].attributes;
                    $(this).addClass('.chosen');
                }
            });
            return this;
        },
        addOne: function(hero) {
            let personView = new HeroView({model: hero});
            this.$el.append(personView.render().el);
        },
        onError: function () {
            console.log('ERROR FETCH IN CHARACTERS');
        }
    });

    let heroesView = new HeroesView({collection: new HeroesCollection});
    let center = $('#center');
    center.html(heroesView.render().el);
    $('<button/>', {id: 'chooseHero'}).html('Выбрать героя!').click(function (event) {
        if(playerHero && !isChosed) {
            gameSocket.send(JSON.stringify(playerHero));
            isChosed = true;
            $(this).prop('disabled', true);
            if(enemyHero) {
                startGame(playerHero, enemyHero);
            }
        }
    }).appendTo(center);

    function startGame(playerHero, enemyHero) {

        let general = $('#general');
        $('#all_characters, #chooseHero').remove();
        let playerHeroModel = new Hero(playerHero);
        let enemyHeroModel = new Hero(enemyHero);
        debugger;
        let playerHeroView = new HeroView({model: playerHeroModel, className: 'characterInGame'});
        let enemyHeroView = new HeroView({model: enemyHeroModel, className: 'characterInGame'});
        let divInLeft = $('<div/>', {
            class: 'inLeft'
        }).append(playerHeroView.render().el);
        let divInRight = $('<div/>', {
            class: 'inRight firstInRight'
        }).append(enemyHeroView.render().el);

        $('<div/>',{
            id: 'left',
            class: 'block'
        }).prependTo(general).append(divInLeft);
        $('<div/>', {
            id: 'right',
            class: 'block'
        }).appendTo(general).append(divInRight);
        let width = CELL_SIZE * map.x;
        let height = CELL_SIZE * map.y;
        let game = new Phaser.Game(width, height, Phaser.AUTO, 'center');
        gameState.prototype.playerHero = playerHero;
        gameState.prototype.enemyHero = enemyHero;
        game.state.add('Game', gameState);
        game.state.start('Game');
    }
}