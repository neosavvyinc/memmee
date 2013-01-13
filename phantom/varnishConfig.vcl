#Looks extremely helpful: http://kly.no/varnish/regex.txt

backend default {
      .host = "127.0.0.1";
      .port = "9090";
}

backend webserver {
      .host = "127.0.0.1";
      .port = "7777";
}


sub vcl_recv {

    if (req.url ~ "\.(png|gif|jpg|css|ico|svg|jpeg|js|otf|json|woff)$") {
        set req.backend = webserver;
    }
    else if ( req.url ~"\.js") {
        set req.backend = webserver;
    }
    else if ( req.url ~ "ptl" )
    {
        set req.backend = webserver;
    }
    else if ( req.url ~ "^/memmeeuserrest"){
        set req.backend = webserver;
    }
    else if ( req.url ~ "^/memmeerest"){
        set req.backend = webserver;
    }
    else if ( req.url ~ "^/memmeeinspirationrest"){
        set req.backend = webserver;
    }
    else if ( req.url ~ "^/reporting"){
        set req.backend = webserver;
    }
    else if ( req.url ~ "^/memmee"){
        set req.backend = webserver;
    }
    else
    {
        set req.backend = default;
    }
}

sub vcl_fetch {
    if ( beresp.ttl < 3600s ) {

        set beresp.ttl = 3600s;
    }
}



