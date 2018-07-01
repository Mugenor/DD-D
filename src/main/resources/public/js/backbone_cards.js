(function() {
    window.App = {
        Models: {},
        Views: {},
        Collections: {}
    };

    window.template = function (id) {
        return _.template($('#' + id).html());
    };

    App.Models.Card = Backbone.Model.extend({

    });

    App.Views.CardView = Backbone.View.extend({
        className: 'card',
        template: template('cards'),
        initialize: function() {
            this.render();
        },
        render: function() {
            this.$el.html(this.template(this.model.toJSON()));
            return this;
        }
    });

    App.Collections.CardsCollection = Backbone.Collection.extend({
        model: App.Models.Card
    });

    App.Views.CardsView = Backbone.View.extend({
        id: 'all_cards',
        initialize: function() {
        },
        render: function() {
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(card) {
            var personView = new App.Views.CardView({model: card});
            this.$el.append(personView.render().el);
        }
    });

//var people = []; - с сервера
//var peopleCollection = new PeopleCollection(people);

    window.cardsCollection = new App.Collections.CardsCollection([
        {
            id: 1,
            name: 'Some name',
            image: '../img/favicon.png',
            description: 'Some description',
            damage: 0
        },
        {
            id: 2,
            name: 'Some name',
            image: '../img/favicon.png',
            description: 'Some description',
            damage: 0
        },
        {
            id: 3,
            name: 'Some name',
            image: '../img/favicon.png',
            description: 'Some description',
            damage: 0
        },
        {
            id: 4,
            name: 'Some name',
            image: '../img/favicon.png',
            description: 'Some description',
            damage: 0
        },
        {
            id: 5,
            name: 'Some name',
            image: '../img/favicon.png',
            description: 'Some description',
            damage: 0
        },
        {
            id: 6,
            name: 'Меч-резанец',
            image: '../img/favicon.png',
            description: 'Отрубает сопернику мезинец правой руки',
            damage: 1
        },
        {
            id: 13,
            name: 'Атака орков',
            image: '../img/орг.jpg',
            description: 'Топорами и булавами наносят противнику многочисленные удары в голову',
            damage: 3
        },
        {
            id: 14,
            name: 'Щит из щитов',
            image: '../img/favicon.png',
            description: 'сносит всё на своём пути – убирает первое препятствие на своём пути по прямой от игрока, ' +
            'направление которой он выбирает. Если на пути оказалось преграда, он просто её уничтожает, если соперник – 4 ед.урона. ' +
            'Если на прямой оказались только границы поля, ничего не происходит',
            damage: 4
        },
        {
            id: 20,
            name: 'Прыгающие ботинки',
            image: '../img/favicon.png',
            description: 'Способны переместить игрока на любую клетку поля',
            damage: 1
        },
        {
            id: 24,
            name: 'Супер меч-резанец',
            image: '../img/favicon.png',
            description: 'Отрубает сопернику левую руку',
            damage: 5
        },
        {
            id: 38,
            name: 'Кексы смерти',
            image: '../img/favicon.png',
            description: 'Автоматическая победа в игре',
            damage: 30
        }
    ]);

    window.cardsView = new App.Views.CardsView({collection: cardsCollection});
    $('#center').html(cardsView.render().el);
}());