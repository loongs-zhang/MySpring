package com.dragon.springframework.lombok;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author SuccessZhang
 * @date 2020/07/19
 */
public class GetterProcessor extends AbstractProcessor {

    /**
     * 用于在编译期打log。
     */
    private Messager messager;

    /**
     * 待处理的抽象语法树。
     */
    private JavacTrees trees;

    /**
     * 用于创建抽象语法树的节点。
     */
    private TreeMaker treeMaker;

    /**
     * 用于创建标识符。
     */
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    /**
     * 支持的注解类型。
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> stringSet = new LinkedHashSet<>();
        stringSet.add(Getter.class.getName());
        return stringSet;
    }

    /**
     * 最低支持的JDK版本。
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(Getter.class);
        set.forEach(element -> {
            JCTree jcTree = trees.getTree(element);
            jcTree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();
                    for (JCTree tree : jcClassDecl.defs) {
                        if (tree.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) tree;
                            jcVariableDeclList = jcVariableDeclList.append(jcVariableDecl);
                        }
                    }
                    jcVariableDeclList.forEach(jcVariableDecl -> {
                        messager.printMessage(Diagnostic.Kind.NOTE, jcVariableDecl.getName() + " has been processed");
                        jcClassDecl.defs = jcClassDecl.defs.prepend(defineGetterMethod(jcVariableDecl));
                    });
                    super.visitClassDef(jcClassDecl);
                }

            });
        });
        return true;
    }

    /**
     * 在AST语法树中定义Getter方法。
     */
    private JCTree.JCMethodDecl defineGetterMethod(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        statements.append(treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName())));
        JCTree.JCBlock body = treeMaker.Block(0, statements.toList());
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewMethodName(jcVariableDecl.getName()), jcVariableDecl.vartype, List.nil(), List.nil(), List.nil(), body, null);
    }

    /**
     * 生成getXxxx方法名。
     */
    private Name getNewMethodName(Name name) {
        String s = name.toString();
        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }

}
