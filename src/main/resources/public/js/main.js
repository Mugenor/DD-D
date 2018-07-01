$(function() {
    window.App = {
        Models: {},
        Views: {},
        Collections: {}
    };

    window.template = function (id) {
        return _.template($('#' + id).html());
    };

    App.Models.Character = Backbone.Model.extend({
        initialize: function () {
            console.log('Модель Character создана')
        }
    });

    App.Views.Character = Backbone.View.extend({
        tagName: 'div',
        className: 'character',
        template: template('charactersTemplate'),
        initialize: function() {
            this.$el.addClass(this.model.get('className'));
        },
        render: function() {
            var template = this.template(this.model.toJSON());
            this.$el.html(template);
            return this;
        }
    });

    App.Collections.CharactersCollection = Backbone.Collection.extend({
        model: App.Models.Character
    });

    App.Views.Characters = Backbone.View.extend({
        tagName: 'div',
        initialize: function() {
            this.$el.addClass(this.className);
        },
        render: function() {
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(character) {
            var characterView = new App.Views.Character({ model: character});
            this.$el.append(characterView.render().el);
        }
    });

    window.charactersCollection = new App.Collections.CharactersCollection([
        {
            title: 'Герой 1'
        },
        {
            title: 'Герой 2'
        },
        {
            title: 'Герой 3'
        },
        {
            title: 'Герой 4'
        },
        {
            title: 'Герой 5'
        }
    ]);

    window.charactersView = new App.Views.Characters({ collection: charactersCollection});
    $('#center').html(charactersView.render().el);
    console.log(charactersView.el);

    //   $('.tasks').append(tasksView.render().el);
    // console.log(tasksView.el);

});