function UserController($xhr) {


    this.user = {
        firstName: '',
        lastName: '',
        emailAddress: ''
    };

    this.users = {};

    var self = this;
    self.loadUsers = function() {
        $xhr('GET', '/memmeerest/user'
            ,function(code, response) {
                self.response = response;
                self.code = code;
                self.message = "Success was awesome!";
                self.users = response;
            },
            function(code, response) {
                self.response = response;
                self.code = code;
                self.message = "Error occurred";
            }

        );
    }

    self.registerUser = function()
    {
        $xhr.defaults.headers.post['Content-Type']='application/json';
        $xhr('POST', '/memmeerest/user', self.user,
            function(code, response) {
                self.response = response;
                self.code = code;
                self.message = "Success was awesome!";
                self.loadUsers();
            },
            function(code, response) {
                self.response = response;
                self.code = code;
                self.message = "Error occurred";
            }
        );
    }


    self.loadUsers();
}