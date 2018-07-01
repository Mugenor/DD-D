(function() {
    window.App = {
        Models: {},
        Views: {},
        Collections: {}
    };

    window.template = function (id) {
        return _.template($('#' + id).html());
    };

    App.Models.Person = Backbone.Model.extend({

    });

    App.Views.PersonView = Backbone.View.extend({
        className: 'character',
        template: template('characters'),
        initialize: function() {
            this.render();
        },
        render: function() {
            this.$el.html(this.template(this.model.toJSON()));
            return this;
        }
    });

    App.Collections.PeopleCollection = Backbone.Collection.extend({
        url: '/character',
        model: App.Models.Person
    });

    App.Views.PeopleView = Backbone.View.extend({
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
            return this;
        },
        addOne: function(person) {
            var personView = new App.Views.PersonView({model: person});
            this.$el.append(personView.render().el);
        },
        onError: function () {
            console.log('ERROR FETCH IN CHARACTERS');
        }
    });

    window.peopleView = new App.Views.PeopleView({collection: new App.Collections.PeopleCollection});
    $('#center').html(peopleView.render().el);
}());