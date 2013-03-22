PN.Router = Ember.Router.extend({
    location: 'hash'
});

PN.Router.map(function() {
    this.route("landing", {path: "/"});
    this.resource("link", {path: "/link"}, function() {
        this.route("index", {path: '/'});
        this.route("detail", {path: '/:link_id'});
    });
});

PN.LandingRoute = Ember.Route.extend({
    renderTemplate: function() {
        this.render('landing')
    }
})

PN.LinkIndexRoute = Ember.Route.extend({
    model: function() {
        console.log('LinkIndexRoute model');
        return PN.Link.find();
    }
});

PN.LinkDetailRoute = Ember.Route.extend({
    setupController: function(controller, model) {
        console.log('PN.LinkDetailRoute setupControllers');
        controller.set('content', model);
    }
});