(function() {
    window.App = {
        Models: {},
        Views: {},
        Collections: {}
    };

    window.template = function(id) {
        return _.template($('#' + id).html());
    };


    App.Models.Person = Backbone.Model.extend( {

        defaults: {
            name: 'Dima',
            age: 24,
            job: 'web developer'
       },

      initialize: function() {
          console.log("A new model was created");
           this.on('invalid', function (model, invalid) {console.log(invalid)});
       },

      validate: function( attrs ) {
         console.log(attrs);
         if ( attrs.age <= 0 ) {
                return 'Wrong age';
            }
          if (!attrs.name) {
             return 'Person must have a name';
         }
     },
    
      walk: function () {
     return this.get('name') + ' is walking';
    }
    });

    App.Collections.People = Backbone.Collection.extend({
     model: App.Models.Person
    });


    App.Views.People = Backbone.View.extend({
        tagName: 'ul',

    initialize: function() {
          console.log(this.collection);
       },

       render: function() {
           this.collection.each(function(person) {
              var personView = new App.Views.Person ({model: person});
              console.log(personView);
              this.$el.append(personView.render().el);
          }, this);
          return this;
      }
    });

    App.Views.Person = Backbone.View.extend({
       initialize: function() {
          this.render();
       },

       template: template('person-id'),

       tagName: 'li',

      render: function() {
           this.$el.html(this.template(this.model.toJSON()));
           return this;
       }
    });

    var people = new App.Collections.People([
      {name: 'Elisa',
       age: 19},
      {},
      {name: 'Georg',
      age: 22}
    ]);

    var peopleView = new App.Views.People({collection: people});

    $('body').append(peopleView.render().el);

    console.log(App.Models);
}());



// $(document.body).append(personView.el)

// this.on('error', function (model, error) {console.log(error)});

/*
var Person = function (config) {
    this.name = config.name;
    this.age = config.age;
    this.job = config.job;
};

Person.prototype.walk = function () {
    return this.name + ' is walking';
};

var nick = new Person ( {name:'Nick', age:'24', job:'Front-end developer'} );*/
