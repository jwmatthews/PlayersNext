PN.Router = Ember.Router.extend({
    location: 'hash'
});

PN.Router.map(function() {
    this.route("index", {path: "/"});
    this.resource("link", {path: "/link"}, function() {
        this.route("index", {path: '/link'});
        this.route("detail", {path: '/link/:link_id'});
    });
});

PN.IndexRoute = Ember.Route.extend({
    redirect: function() {
        console.log('indexRoute');
        this.transitionTo('link.index');
    }
});

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