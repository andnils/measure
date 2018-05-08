# measure

A project template for a clojure web application, with a postgresql DB and javascript+react in the frontend.

## Setup

### Prerequisites

Either use the supplier docker setup, or setup your own environment.

#### Using Docker
* Docker / docker-compose

To start the development environment, use:

```shell
docker-compose up
```

* Containers

To enter a bash shell in the node container:

```shell
docker exec -it mnode bash
```

Find the IP address of the clojure container

```shell
docker inspect mclojure | grep IPAddress
```

#### Using locally installed stuff

* Java 8
* leiningen (clojure build tool)
* node/npm (npm 5.7.1)
* postgresql 10

Make sure that you set up a database, and enter you connection details in `config/private.edn` (there is a template you can copy named `config/private.cfg.template`). Note that `private.edn` is not meant to be put under source control.

### Technical namedropping

React, eslint, webpack, babel for the front end.
Postgres database.
Clojure using the standard Ring/Compojure libraries for http.
Ragtime handles the database migrations.
Jetty is the web server.



### Backend

If you just want to start the backend server you can just:

    lein run

If you plan on doing backend development, you should run it from the clojure REPL.
Start the REPL from your IDE/editor, or run it in a terminal:

    lein repl

When the REPL boots, load the `dev` namespace, run database migrations, and start the http server:

    user> (require 'measure.system)
    nil
    user> (require 'measure.database-migrations)
    nil
    user> (measure.database-migrations/db-migrate config)
    Applying 001-heroes
    user> (measure.system/start-jetty)
    ...

When you've made changes to the (clojure-)code, call the `reset` function to reload the code:

    dev> (reset)
    :reloading ...
    :ok

If you're using Emacs with Cider, bind the reset-command to a keyboard shortcut:

    (defun clojure-component-reset ()
      "Reset the system."
      (interactive)
      (cider-switch-to-repl-buffer)
      (goto-char (point-max))
      (insert "(reset)")
      (cider-repl-return))

    (global-set-key (kbd "C-c C-r C-r") 'clojure-component-reset)

### Frontend

Start with installing js dependencies:

    npm install

Next, start the webpack dev server:

    npm run serve

Now a browser window will open, serving files from `dist`-directory. The dev server will proxy all request where the path begins with `/api` to the backend server.


## Usage

TODO
