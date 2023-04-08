var colorPlugin = (function(){
    var plugin = {};

    plugin.init = function(worksheet){
console.log("init event");
    }

    plugin.onevent = function(){
       console.log("event");
    }

    return plugin;
});


