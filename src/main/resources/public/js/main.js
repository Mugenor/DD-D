$(function() {
    window.App = {
        Models: {},
        Views: {},
        Collections: {}
    };

    window.template = function (id) {
        return _.template($('#' + id).html());
    };

    App.Models.Menu = Backbone.Model.extend({
        initialize: function () {
            console.log('Модель создана')
        }
    });

    App.Views.Menu = Backbone.View.extend({
        tagName: 'li',
        template: template('menuTemplate'),
        initialize: function() {
            this.$el.addClass(this.model.get('className'));
        },
        render: function() {
            var template = this.template(this.model.toJSON());
            this.$el.html(template);
            return this;
        },
        events: {
            'click': 'clickOn'
        },
        clickOn: function() {
            this.model.attributes.clickOnMenu();
        }
    });

    App.Collections.MenuCollection = Backbone.Collection.extend({
        model: App.Models.Menu
    });

    App.Views.Menus = Backbone.View.extend({
        tagName: 'ul',
        initialize: function() {
            this.$el.addClass(this.className);
        },
        render: function() {
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(menu) {
            var menuView = new App.Views.Menu({ model: menu});
            this.$el.append(menuView.render().el);
        }
    });

    window.menuCollection = new App.Collections.MenuCollection([
        {
            title: 'Главная',
            className: 'leftEdge',
            clickOnMenu: function() {
                alert('Hello');
            }
        },
        {
            title: 'Новости'
        },
        {
            title: 'Игра',
            className: 'forSubmenu'
        },
        {
            title: 'Обратная связь'
        },
        {
            title: 'О нас',
            className: 'rightEdge'
        }
    ]);

    window.submenuCollection = new App.Collections.MenuCollection([
        {
            title: 'Сыграем'
        },
        {
            title: 'Правила'
        },
        {
            title: 'Герои'
        },
        {
            title: 'Карточки'
        }
    ]);

    window.menusView = new App.Views.Menus({ collection: menuCollection, className: 'main_menu'});
    window.submenusView = new App.Views.Menus({ collection: submenuCollection, className: 'submenu'});
    $('#menu').html(menusView.render().el);
    $('.forSubmenu').append(submenusView.render().el);
    console.log(menusView.el);
    console.log(submenusView.el);

    //   $('.tasks').append(tasksView.render().el);
    // console.log(tasksView.el);
});