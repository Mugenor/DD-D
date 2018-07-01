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
        model: App.Models.Person
    });

    App.Views.PeopleView = Backbone.View.extend({
        id: 'all_characters',
        initialize: function() {
        },
        render: function() {
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(person) {
            var personView = new App.Views.PersonView({model: person});
            this.$el.append(personView.render().el);
        }
    });

//var people = []; - с сервера
//var peopleCollection = new PeopleCollection(people);

    window.peopleCollection = new App.Collections.PeopleCollection([
        {
            image: '../img/Диппер2.png',
            name: 'Диппер',
            feature: 'Очень умный'
        },
        {
            image: '../img/Мэйбл2.png',
            name: 'Мэйбл',
            feature: 'Безнадежная оптимистка'
        },
        {
            image: '../img/Дядя Стэн2.png',
            name: 'Дядя Стэн',
            feature: 'Жулик и мошенник'
        },
        {
            image: '../img/Венди2.png',
            name: 'Венди',
            feature: 'Хладнокровность'
        },
        {
            image: '../img/Зус2.png',
            name: 'Зус',
            feature: 'Доброта'
        },
        {
            image: '../img/Дядя Форд2.png',
            name: 'Дедушка Форд',
            feature: 'Гений и изобретатель'
        }
    ]);

    window.peopleView = new App.Views.PeopleView({collection: peopleCollection});
    $('#center').html(peopleView.render().el);
}());