package tree;

import javax.swing.tree.TreeNode;

import estrut.Tree;

public class BinarySearchTree implements Tree {

    private TreeNode root;

    @Override
    public boolean buscaElemento(int valor) {
        return buscaElementoRecursivamente(root, valor);
    }

    private boolean buscaElementoRecursivamente(Node node, int valor) {
        if (node == null)
            return false;

        if (valor == node.value)
            return true;

        if (valor < node.value)
            return buscaElementoRecursivamente(node.left, valor);
        else
            return buscaElementoRecursivamente(node.right, valor);
    }

    @Override
    public int minimo() {
        if (root == null)
            throw new IllegalStateException("Árvore vazia");

        Node currentNode = root;
        while (currentNode.left != null)
            currentNode = currentNode.left;

        return currentNode.value;
    }

    @Override
    public int maximo() {
        if (root == null)
            throw new IllegalStateException("Árvore vazia");

        Node currentNode = root;
        while (currentNode.right != null)
            currentNode = currentNode.right;

        return currentNode.value;
    }

    @Override
    public void insereElemento(int valor) {
        root = inserirRecursivamente(root, valor);
    }

    private Node inserirRecursivamente(Node node, int valor) {
        if (node == null)
            return new Node(valor);

        if (valor < node.value) {
            node.left = inserirRecursivamente(node.left, valor);
        } else if (valor > node.value) {
            node.right = inserirRecursivamente(node.right, valor);
        } else {
            // Valor já existe na árvore, não fazemos nada
            return node;
        }

        return node;
    }

    @Override
    public void remove(int valor) {
        root = removerRecursivamente(root, valor);
    }

    private Node removerRecursivamente(Node node, int valor) {
        if (node == null)
            return node;

        if (valor < node.value) {
            node.left = removerRecursivamente(node.left, valor);
        } else if (valor > node.value) {
            node.right = removerRecursivamente(node.right, valor);
        } else {
            // Nó a ser removido
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;

            // Nó com dois filhos
            node.value = minimo(node.right);

            node.right = removerRecursivamente(node.right, node.value);
        }

        return node;
    }

    private int minimo(Node node) {
        int min = node.value;
        while (node.left != null) {
            min = node.left.value;
            node = node.left;
        }
        return min;
    }

    @Override
    public int[] preOrdem() {
        int[] result = new int[countNodes(root)];
        preOrdemRecursivo(root, result, 0);
        return result;
    }

    private int preOrdemRecursivo(Node node, int[] result, int index) {
        if (node == null)
            return index;

        result[index++] = node.value;
        index = preOrdemRecursivo(node.left, result, index);
        index = preOrdemRecursivo(node.right, result, index);
        return index;
    }

    @Override
    public int[] emOrdem() {
        int[] result = new int[countNodes(root)];
        emOrdemRecursivo(root, result, 0);
        return result;
    }

    private int emOrdemRecursivo(Node node, int[] result, int index) {
        if (node == null)
            return index;

        index = emOrdemRecursivo(node.left, result, index);
        result[index++] = node.value;
        index = emOrdemRecursivo(node.right, result, index);
        return index;
    }

    @Override
    public int[] posOrdem() {
        int[] result = new int[countNodes(root)];
        posOrdemRecursivo(root, result, 0);
        return result;
    }

    private int posOrdemRecursivo(Node node, int[] result, int index) {
        if (node == null)
            return index;

        index = posOrdemRecursivo(node.left, result, index);
        index = posOrdemRecursivo(node.right, result, index);
        result[index++] = node.value;
        return index;
    }

}

class TreeNode{
    int data;
    TreeNode left;
    TreeNode right;

public TreeNode(int data){
    this.data = data;
    left = null;
    right = null;
}
}
