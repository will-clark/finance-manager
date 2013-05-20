Deployment Instructions

Setup

    Install the Heroku Toolbelt

    Install the heroku-deploy command line plugin:

    $ heroku plugins:install https://github.com/heroku/heroku-deploy
    
    Create a new heroku application
    
    $ heroku create

Build the WAR file

   $ ant build

Deploy the WAR file

    $ heroku deploy:war --war build/finance.war
        

# Note: If running standalone in Tomcat, set a VM argument defining the DB URL:

-DDATABASE_URL=postgres://localdev:localdev@localhost:5432/localdev

