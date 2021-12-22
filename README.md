# jlox

A script interpreter for learning purpose ([Crafting Interpreters](https://github.com/munificent/craftinginterpreters)).

## Example scripts

See `scripts` folder for example scripts.

### Closure
```c++
fun makeCounter() {
    var i = 0;
    fun count() {
        i = i + 1;
        print i;
    }
    return count;
}

var counter = makeCounter();
counter();
counter();
counter();
```

### Lexical (static) Scope
```c++
var a = 1;
fun resolve() {
    print a;
}

fun test() {
    resolve();
    var a = 2;
    resolve();
}

test();
```

### Class & Inheritance
```c++
class Greeter {
    init(name) {
        this.name = name;
    }

    greet() {
        print "Hello, " + this.name + "!";
    }
}

class NameGreeter < Greeter {
    init(firstName, lastName) {
        super.init(firstName + " " + lastName);
    }
}

print Greeter;
print NameGreeter;
var g = NameGreeter("Dear", "Esther");
g.greet();
```
