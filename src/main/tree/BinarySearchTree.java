package tree;

import estrut.Tree;

public class BinarySearchTree implements Tree {
    private TreeNode root;

    @Override
    public boolean buscaElemento(int valor) {
        return buscaElementoRecursivo(root, valor);
    }

    private boolean buscaElementoRecursivo(TreeNode node, int valor) {
        if (node == null) {
            return false;
        }

        if (valor == node.data) {
            return true;
        } else if (valor < node.data) {
            return buscaElementoRecursivo(node.left, valor);
        } else {
            return buscaElementoRecursivo(node.right, valor);
        }
    }

    @Override
    public int minimo() {
        if (root == null) {
            throw new NullPointerException("Árvore vazia");
        }

        TreeNode current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.data;
    }

    @Override
    public int maximo() {
        if (root == null) {
            throw new NullPointerException("Árvore vazia");
        }

        TreeNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }

    @Override
    public void insereElemento(int valor) {
        root = inserirRecursivo(root, valor);
    }

    private TreeNode inserirRecursivo(TreeNode node, int valor) {
        if (node == null) {
            return new TreeNode(valor);
        }

        if (valor < node.data) {
            node.left = inserirRecursivo(node.left, valor);
        } else if (valor > node.data) {
            node.right = inserirRecursivo(node.right, valor);
        }

        // Atualizar altura do nó
        node.altura = 1 + Math.max(getAltura(node.left), getAltura(node.right));

        // Verificar balanceamento e rotacionar se necessário
        int balance = getBalance(node);

        if (balance > 1 && valor < node.left.data) {
            return rotacaoDireita(node);
        }

        if (balance < -1 && valor > node.right.data) {
            return rotacaoEsquerda(node);
        }

        if (balance > 1 && valor > node.left.data) {
            node.left = rotacaoEsquerda(node.left);
            return rotacaoDireita(node);
        }

        if (balance < -1 && valor < node.right.data) {
            node.right = rotacaoDireita(node.right);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    @Override
    public void remove(int valor) {
        root = removerRecursivo(root, valor);
    }

    private TreeNode removerRecursivo(TreeNode node, int valor) {
        if (node == null) {
            return node;
        }

        if (valor < node.data) {
            node.left = removerRecursivo(node.left, valor);
        } else if (valor > node.data) {
            node.right = removerRecursivo(node.right, valor);
        } else {
            if (node.left == null || node.right == null) {
                TreeNode temp = null;
                if (temp == node.left) {
                    temp = node.right;
                } else {
                    temp = node.left;
                }

                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                TreeNode temp = minValueNode(node.right);
                node.data = temp.data;
                node.right = removerRecursivo(node.right, temp.data);
            }
        }

        if (node == null) {
            return node;
        }

        node.altura = 1 + Math.max(getAltura(node.left), getAltura(node.right));

        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotacaoDireita(node);
        }

        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotacaoEsquerda(node.left);
            return rotacaoDireita(node);
        }

        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotacaoEsquerda(node);
        }

        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotacaoDireita(node.right);
            return rotacaoEsquerda(node);
        }

        return node;
    }

    private TreeNode minValueNode(TreeNode node) {
        TreeNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private TreeNode rotacaoDireita(TreeNode y) {
        TreeNode x = y.left;
        TreeNode T = x.right;

        x.right = y;
        y.left = T;

        y.altura = Math.max(getAltura(y.left), getAltura(y.right)) + 1;
        x.altura = Math.max(getAltura(x.left), getAltura(x.right)) + 1;

        return x;
    }

    private TreeNode rotacaoEsquerda(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T = y.left;

        y.left = x;
        x.right = T;

        x.altura = Math.max(getAltura(x.left), getAltura(x.right)) + 1;
        y.altura = Math.max(getAltura(y.left), getAltura(y.right)) + 1;

        return y;
    }

    private int getAltura(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node.altura;
    }

    private int getBalance(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return getAltura(node.left) - getAltura(node.right);
    }

    @Override
    public int[] preOrdem() {
        return preOrdemRecursivo(root);
    }

    private int[] preOrdemRecursivo(TreeNode node) {
        if (node == null) {
            return new int[] {};
        }

        int[] leftArray = preOrdemRecursivo(node.left);
        int[] rightArray = preOrdemRecursivo(node.right);
        int[] result = new int[leftArray.length + rightArray.length + 1];

        result[0] = node.data;
        System.arraycopy(leftArray, 0, result, 1, leftArray.length);
        System.arraycopy(rightArray, 0, result, leftArray.length + 1, rightArray.length);

        return result;
    }

    @Override
    public int[] emOrdem() {
        return emOrdemRecursivo(root);
    }

    private int[] emOrdemRecursivo(TreeNode node) {
        if (node == null) {
            return new int[] {};
        }

        int[] leftArray = emOrdemRecursivo(node.left);
        int[] rightArray = emOrdemRecursivo(node.right);
        int[] result = new int[leftArray.length + rightArray.length + 1];

        System.arraycopy(leftArray, 0, result, 0, leftArray.length);
        result[leftArray.length] = node.data;
        System.arraycopy(rightArray, 0, result, leftArray.length + 1, rightArray.length);

        return result;
    }

    @Override
    public int[] posOrdem() {
        return posOrdemRecursivo(root);
    }

    private int[] posOrdemRecursivo(TreeNode node) {
        if (node == null) {
            return new int[] {};
        }

        int[] leftArray = posOrdemRecursivo(node.left);
        int[] rightArray = posOrdemRecursivo(node.right);
        int[] result = new int[leftArray.length + rightArray.length + 1];

        System.arraycopy(leftArray, 0, result, 0, leftArray.length);
        System.arraycopy(rightArray, 0, result, leftArray.length, rightArray.length);
        result[leftArray.length + rightArray.length] = node.data;

        return result;
    }
}

class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;
    int altura;

    public TreeNode(int data) {
        this.data = data;
        this.altura = 1;
        left = null;
        right = null;
    }
}
