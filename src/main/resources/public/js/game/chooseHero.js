let gameSocket;
function chooseHero() {
    function startGame(playerHero, enemyHero) {
        $('#all_characters').remove();
        let width = CELL_SIZE * map.x;
        let height = CELL_SIZE * map.y;
        let game = new Phaser.Game(width, height, Phaser.AUTO, 'center');
        gameState.prototype.playerHero = playerHero;
        gameState.prototype.enemyHero = enemyHero;
        game.state.add('Game', gameState);
        game.state.start('Game');
    }

    let enemyHero;
    let playerHero;
    gameSocket = new WebSocket('ws://localhost:8080/game');
    gameSocket.onopen = function() {
        console.log('Game socket opened');
    };
    gameSocket.onmessage = function(event) {
        debugger;
        enemyHero = JSON.parse(event.data);
        if(enemyHero && playerHero) {
            startGame(playerHero, enemyHero);
        }
    };

    window.template = function (id) {
        return _.template($('#' + id).html());
    };

    Hero = Backbone.Model.extend({

    });

    HeroView = Backbone.View.extend({
        className: 'character',
        template: template('characters'),
        initialize: function() {
            this.render();
        },
        render: function() {
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
            $('.character').click(function (event) {
                console.log(this, event);
                playerHero = th.collection.models[this.id].attributes;
                gameSocket.send(JSON.stringify(playerHero));
                if(playerHero && enemyHero) {
                    startGame(playerHero, enemyHero);
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
    $('#center').html(heroesView.render().el);
}