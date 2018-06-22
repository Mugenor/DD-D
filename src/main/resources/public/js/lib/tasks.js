$(function() {
    window.App = {
        Models: {},
        Views: {},
        Collections: {}
    };

    window.template = function(id) {
        return _.template($('#' + id).html());
    };

    App.Models.Task = Backbone.Model.extend({
        initialize: function() {
            this.on('invalid', function (model, invalid) {
                alert(invalid);
            });
        },
        validate: function(attrs) {
            if (! $.trim(attrs.title)) {
                return 'Model must have a title';
            }
        }
    });
    App.Views.Task = Backbone.View.extend({
        initialize: function() {
        //    _.bindAll(this, 'editTask', 'render');
            this.model.on({'change': this.render,
                           'destroy': this.remove}, this);
          //  this.model.off();
          //  this.model.on('destroy', this.remove, this);
        },
        remove: function() {
            this.$el.remove();
        },
        tagName: 'li',
        template: template('taskTemplate'),
        render: function() {
            var template = this.template(this.model.toJSON());
            this.$el.html(template);
            return this;
        },
        events: {
            'click .edit': 'editTask',
            'click .delete': 'deleteTask'
        },
        deleteTask: function() {
            this.model.destroy();
        },
        editTask: function() {
            var newTask = prompt('What is a new name?', this.model.get('title'));
            this.model.set('title', newTask, {validate:true});
        }
    });
    App.Collections.Task = Backbone.Collection.extend({
        model: App.Models.Task
    });
    App.Views.Tasks = Backbone.View.extend({
        tagName: 'ul',
        initialize: function() {
          this.collection.on('add', this.addOne, this);
        },
        render: function() {
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(task) {
            var taskView = new App.Views.Task({ model: task });
            this.$el.append(taskView.render().el);
        }

    });
    App.Views.AddTask = Backbone.View.extend({
        el: '#addTask',
        events: {
          'submit': 'submit'
        },
        initialize: function() {
            console.log(this.el);
        },
        submit: function(e) {
            e.preventDefault();
          //  var newTaskTitle = this.$el.find('input[type=text]').val();
            var newTaskTitle = $(e.currentTarget).find('input[type=text]').val();
            var newTask = new App.Models.Task({
                title: newTaskTitle
            });
            this.collection.add(newTask);
        }
    });

    window.tasksCollection = new App.Collections.Task([
        {
            title: 'Go to a shop',
            priority: 4
        },
        {
            title: 'Give some messages',
            priority: 3
        },
        {
            title: 'Go to my work',
            priority: 5
        }
    ]);

    window.tasksView = new App.Views.Tasks({ collection: tasksCollection });
    var addTaskView = new App.Views.AddTask({ collection: tasksCollection});


    //   $('.tasks').append(tasksView.render().el);
    $('.tasks').html(tasksView.render().el);
   // console.log(tasksView.el);

});

//  $('body').html(tasksView.el);
// this.model.set('title', newTask, {silent:true}); - конкретно сейчас обработка событий в инициализации не произойдёт благодаря аргументу

/*
var Sidebar = Backbone.Model.extend({
    promptColor: function() {
        var cssColor = prompt("Пожалуйста, введите CSS-цвет:");
        this.set({color: cssColor});
    }
});

window.sidebar = new Sidebar;

sidebar.on('change:color', function(model, color) {
    $('#sidebar').css({background: color});
});

sidebar.set({color: 'white'});

sidebar.promptColor();*/
