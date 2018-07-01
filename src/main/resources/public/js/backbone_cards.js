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
        url: '/card',
        model: App.Models.Card
    });

    App.Views.CardsView = Backbone.View.extend({
        id: 'all_cards',
        initialize: function() {
            this.collection.fetch({
                context: this,
                success: this.render,
                error: this.onError
            })
        },
        render: function() {
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(card) {
            let personView = new App.Views.CardView({model: card});
            this.$el.append(personView.render().el);
        },
        onError: function () {
            console.log('ERROR FETCH IN CARDS');
        }
    });

    window.cardsView = new App.Views.CardsView({collection: new App.Collections.CardsCollection()});
    $('#center').html(cardsView.render().el);
}());