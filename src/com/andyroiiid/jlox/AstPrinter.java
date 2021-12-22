package com.andyroiiid.jlox;

class AstPrinter implements Expr.Visitor<String>, Stmt.Visitor<String> {
    String print(Stmt stmt) {
        return stmt.accept(this);
    }

    @Override
    public String visitBlockStmt(Stmt.Block stmt) {
        StringBuilder builder = new StringBuilder();
        builder.append("(block");
        for (Stmt statement : stmt.statements) {
            builder.append(' ');
            builder.append(statement.accept(this));
        }
        builder.append(")");
        return builder.toString();
    }

    @Override
    public String visitClassStmt(Stmt.Class stmt) {
        StringBuilder builder = new StringBuilder();
        if (stmt.superclass != null) {
            builder.append("(inherit:").append(stmt.superclass.name.lexeme);
        } else {
            builder.append("(class");
        }
        builder.append(' ').append(stmt.name.lexeme);
        for (Stmt.Function method : stmt.methods) {
            builder.append(' ');
            builder.append(method.accept(this));
        }
        builder.append(")");
        return builder.toString();
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();
        builder.append('(').append(name);
        for (Expr expr : exprs) {
            builder.append(' ');
            builder.append(expr.accept(this));
        }
        builder.append(')');
        return builder.toString();
    }

    @Override
    public String visitExpressionStmt(Stmt.Expression stmt) {
        return stmt.expression.accept(this);
    }

    @Override
    public String visitFunctionStmt(Stmt.Function stmt) {
        StringBuilder builder = new StringBuilder();
        builder.append("(fun ").append(stmt.name.lexeme).append(" (params");
        for (Token param : stmt.params) {
            builder.append(' ');
            builder.append(param.lexeme);
        }
        builder.append(") (body");
        for (Stmt statement : stmt.body) {
            builder.append(' ');
            builder.append(statement.accept(this));
        }
        builder.append(")");
        builder.append(')');
        return builder.toString();
    }

    @Override
    public String visitIfStmt(Stmt.If stmt) {
        String result = "(if " + stmt.condition.accept(this) + " " + stmt.thenBranch.accept(this);
        if (stmt.elseBranch != null) {
            result += " " + stmt.elseBranch.accept(this);
        }
        result += ")";
        return result;
    }

    @Override
    public String visitPrintStmt(Stmt.Print stmt) {
        return parenthesize("print", stmt.expression);
    }

    @Override
    public String visitReturnStmt(Stmt.Return stmt) {
        if (stmt.value != null) {
            return parenthesize("return", stmt.value);
        } else {
            return "(return)";
        }
    }

    @Override
    public String visitVarStmt(Stmt.Var stmt) {
        return parenthesize("var " + stmt.name.lexeme, stmt.initializer);
    }

    @Override
    public String visitWhileStmt(Stmt.While stmt) {
        return "(while " + stmt.condition.accept(this) + " " + stmt.body.accept(this) + ")";
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return parenthesize(expr.name.lexeme, expr.value);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitCallExpr(Expr.Call expr) {
        StringBuilder builder = new StringBuilder();
        builder.append('(').append(expr.callee.accept(this));
        for (Expr argument : expr.arguments) {
            builder.append(' ');
            builder.append(argument.accept(this));
        }
        builder.append(')');
        return builder.toString();
    }

    @Override
    public String visitGetExpr(Expr.Get expr) {
        return parenthesize("get:" + expr.name.lexeme, expr.object);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitLogicalExpr(Expr.Logical expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitSetExpr(Expr.Set expr) {
        return parenthesize("set:" + expr.name.lexeme, expr.object, expr.value);
    }

    @Override
    public String visitSuperExpr(Expr.Super expr) {
        return "(super " + expr.method.lexeme + ")";
    }

    @Override
    public String visitThisExpr(Expr.This expr) {
        return "this";
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return expr.name.lexeme;
    }
}
