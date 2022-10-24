/* The Computer Language Benchmarks Game
 * https://salsa.debian.org/benchmarksgame-team/benchmarksgame/

   contributed by Kevin Carson
   modified by Wonsun Ahn from binary_tree.c
   compilation:
       gcc -O3 -fomit-frame-pointer -funroll-loops -static binary-trees.c -lm
       icc -O3 -ip -unroll -static binary-trees.c -lm

   *reset*
*/

#include <malloc.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>


typedef struct tn {
    char name[4];
    struct tn*    left;
    struct tn*    right;
} treeNode;


treeNode* NewTreeNode(const char* name, treeNode* left, treeNode* right)
{
    treeNode*    new;

    new = (treeNode*)malloc(sizeof(treeNode));

    strcpy(new->name, name);
    new->left = left;
    new->right = right;

    return new;
} /* NewTreeNode() */


int NameCheck(const char *name, treeNode* tree)
{
    int match;
    if (strcmp(name, tree->name) == 0) {
        match = 1;
    } else {
        printf("Failure: name=%s, tree->name=%s\n", name, tree->name);
        match = 0;
    }
    if (tree->left == NULL) {
        return match;
    } else {
        return match && NameCheck(name, tree->left) && NameCheck(name, tree->right);
    }
} /* ItemCheck() */


treeNode* BottomUpTree(const char *name, unsigned depth)
{
    if (depth > 0)
        return NewTreeNode
        (
            name,
            BottomUpTree(name, depth - 1),
            BottomUpTree(name, depth - 1)
        );
    else
        return NewTreeNode(name, NULL, NULL);
} /* BottomUpTree() */


void DeleteTree(treeNode* tree)
{
    if (tree->left != NULL)
    {
        DeleteTree(tree->left);
        DeleteTree(tree->right);
    }

    free(tree);
} /* DeleteTree() */


int main(int argc, char* argv[])
{
    const char *name;
    unsigned   N, depth, minDepth, maxDepth, stretchDepth;
    treeNode   *stretchTree, *longLivedTree, *tempTree;

    N = atol(argv[1]);
    name = argv[2];

    minDepth = 4;

    if ((minDepth + 2) > N)
        maxDepth = minDepth + 2;
    else
        maxDepth = N;

    stretchDepth = maxDepth + 1;

    stretchTree = BottomUpTree(name, stretchDepth);
    int result = NameCheck(name, stretchTree);
    DeleteTree(stretchTree);

    printf("Name check result = %d\n", result);

    return 0;
} /* main() */
