package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.ai.Exaustive;
import model.ai.Ulehla;
import model.tree.Node;
import model.tree.RandomGenerateTree;
import model.tree.Tree;

/**
 * Interface graphique de l'application.
 * @author Florian, Grégoire
 * @date 11 jan. 2016
 * @version 1.0
 */
   public class ForestGame {
	// ATTRIBUTS

	// Fenêtre principale
	private JFrame mainFrame;

	// Boutons radio
	private JRadioButton gameRadio;
	private JRadioButton demoRadio;

	private JRadioButton j2Radio;
	private JRadioButton iaRadio;

	private JRadioButton ulehlaRadio;
	private JRadioButton exhaustiveRadio;

	// Etiquettes
	private JLabel j1Label;
	private JLabel j2Label;
	private JLabel iaLabel;
	private JLabel resultGameLabel;
	private JLabel winningHitsLabel;

	// Listes déroulantes
	private JComboBox<String> addNodeCombo;
	private JComboBox<String> deleteNodeCombo;

	// Zones de saisies
	private JTextField newLabelNodeTextField;
	private JTextField numberDepthRandomizeTextField;
	private JTextField numberLeafRandomizeTextField;

	// Boutons
	private JButton randomizeButton;
	private JButton expandTreeButton;
	private JButton collapseTreeButton;
	private JButton addNodeButton;
	private JButton deleteNodeButton;
	private JButton searchWinningHitsButton;

	// Panneau déroulant
	private JScrollPane jscroll;

	// Gestionnaire de fichiers
	private JTree tree;

	// Arbre
	private Tree model;

	// CONSTRUCTEURS

	/**
	 * Constructeur de l'interface graphique.
	 * @since 1.0
	 * @version 1.0
	 */
	public ForestGame() {
		createModel();
		createView();
		placeComponents();
		createController();
	}

	// COMMANDES

	/**
	 * Rend l'application visible au centre de l'écran.
	 * @since 1.0
	 * @version 1.0
	 */
	public void display() {
		refresh();
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	// OUTILS

	/**
	 * Création du model de l'application.
	 * @since 1.0
	 * @version 1.0
	 */
	private void createModel() {
		model = new Tree(new Node("-1", 0));
	}

	/**
	 * Création de la vue de l'application.
	 * @since 1.0
	 * @version 1.0
	 */
	private void createView() {
		// Nom de la fenêtre de l'application
		mainFrame = new JFrame("Jeu de la forêt");

		// Taille de la fenêtre de l'application (selon les dimensions de
		// l'écran physique)
		mainFrame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 3 / 4,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 3 / 4));

		// Boutons radios
		gameRadio = new JRadioButton("Jeu");
		demoRadio = new JRadioButton("Démo");

		j2Radio = new JRadioButton();
		iaRadio = new JRadioButton();

		ulehlaRadio = new JRadioButton("ULEHLA");
		exhaustiveRadio = new JRadioButton("EX");

		// Etiquettes
		j1Label = new JLabel("Joueur 1");
		j2Label = new JLabel("Joueur 2");
		iaLabel = new JLabel("IA");
		resultGameLabel = new JLabel("Partie en cours...");
		winningHitsLabel = new JLabel("Calculer un coups gagnant ?");

		// Listes déroulantes
		addNodeCombo = new JComboBox<String>();
		deleteNodeCombo = new JComboBox<String>();

		newLabelNodeTextField = new JTextField(3);

		// Boutons
		addNodeButton = new JButton("Ajouter");
		addNodeButton.setBackground(Color.GREEN);

		deleteNodeButton = new JButton("Supprimer");
		deleteNodeButton.setBackground(Color.BLACK);
		deleteNodeButton.setForeground(Color.WHITE);

		randomizeButton = new JButton("Générer un arbre aléatoirement");
		randomizeButton.setBackground(Color.WHITE);

		searchWinningHitsButton = new JButton("Chercher coups gagnant(s) ?");
		searchWinningHitsButton.setBackground(Color.ORANGE);

		expandTreeButton = new JButton("+");
		expandTreeButton.setBackground(Color.WHITE);
		expandTreeButton.setToolTipText("Déployer l'arbre");

		collapseTreeButton = new JButton("-");
		collapseTreeButton.setBackground(Color.WHITE);
		collapseTreeButton.setToolTipText("Replier l'arbre");

		// Groupes de boutons radios
		ButtonGroup modeButtonGroup = new ButtonGroup();
		modeButtonGroup.add(gameRadio);
		modeButtonGroup.add(demoRadio);
		demoRadio.setSelected(true);

		ButtonGroup playersButtonGroup = new ButtonGroup();
		playersButtonGroup.add(j2Radio);
		playersButtonGroup.add(iaRadio);
		j2Radio.setSelected(true);

		ButtonGroup optionSearchButtonGroup = new ButtonGroup();
		optionSearchButtonGroup.add(ulehlaRadio);
		optionSearchButtonGroup.add(exhaustiveRadio);
		ulehlaRadio.setSelected(true);

		// Zones de saisies
		numberDepthRandomizeTextField = new JTextField(3);
		numberDepthRandomizeTextField.setText("0");
		
		numberLeafRandomizeTextField = new JTextField(3);
		numberLeafRandomizeTextField.setText("0");

		// Génération de l'arbre (gestionnaire de fichier)
		Node root = new Node("1", 0);
		tree = new JTree(new RandomGenerateTree(root));
	}

	/**
	 * Méthode placant les composants créés précedemment sur la fenêtre.
	 * @since 1.0
	 * @version 1.0
	 */
	private void placeComponents() {
		// Cadre pour un panneau
		Border border = BorderFactory.createEtchedBorder();

		// OUEST (gauche)
		JPanel a = new JPanel(new BorderLayout());
		{
			JPanel b = new JPanel(new FlowLayout(FlowLayout.CENTER));
			{
				b.add(gameRadio);
			}
			a.add(b, BorderLayout.NORTH);

			b = new JPanel(new BorderLayout());
			{
				JPanel c = new JPanel(new GridLayout(3, 2));
				{
					JPanel d = new JPanel(new BorderLayout());
					{
						d.add(new JLabel());
						d.setBorder(border);
					}
					c.add(d, BorderLayout.EAST);

					d = new JPanel(new BorderLayout());
					{
						d.add(j1Label);
					}
					c.add(d, BorderLayout.WEST);

					d = new JPanel(new FlowLayout(FlowLayout.RIGHT));
					{
						d.add(j2Radio);
					}
					c.add(d);

					d = new JPanel(new BorderLayout());
					{
						d.add(j2Label);
						d.setBorder(border);
					}
					c.add(d, BorderLayout.WEST);

					d = new JPanel(new FlowLayout(FlowLayout.RIGHT));
					{
						d.add(iaRadio);
						d.setBorder(border);
					}
					c.add(d);

					d = new JPanel(new BorderLayout());
					{
						d.add(iaLabel);
					}
					c.add(d, BorderLayout.WEST);
				}
				b.add(c, BorderLayout.NORTH);

				c = new JPanel();
				{
					c.add(resultGameLabel);
				}
				b.add(c, BorderLayout.SOUTH);
			}
			a.add(b, BorderLayout.CENTER);

			b = new JPanel(new GridLayout(3, 1));
			{
				b.add(new JLabel("COPYRIGHT"));
				b.add(new JLabel("M. Gregoire POMMIER"));
				b.add(new JLabel("M. Nicolas GILLE"));
				b.add(new JLabel("M. Florian SOUDAY"));
			}
			a.add(b, BorderLayout.SOUTH);
		}
		mainFrame.add(a, BorderLayout.WEST);

		// CENTRE
		jscroll = new JScrollPane();
		{
			jscroll.add(tree);
		}
		mainFrame.add(jscroll, BorderLayout.CENTER);

		// EST (droite)
		JPanel b = new JPanel(new BorderLayout());
		{
			JPanel c = new JPanel(new FlowLayout(FlowLayout.CENTER));
			{
				c.add(demoRadio);
			}
			b.add(c, BorderLayout.NORTH);

			c = new JPanel(new GridLayout(13, 1));
			{

				JPanel d = new JPanel(new GridLayout(2, 2));
				{
					JPanel e = new JPanel();
					{
						e.add(new JLabel("Profondeur : "));
					}
					d.add(e);

					e = new JPanel();
					{
						e.add(numberDepthRandomizeTextField);
					}
					d.add(e);

					e = new JPanel();
					{
						e.add(new JLabel("Feuilles max : "));
					}
					d.add(e);

					e = new JPanel();
					{
						e.add(numberLeafRandomizeTextField);
					}
					d.add(e);
				}
				c.add(d);

				d = new JPanel(new FlowLayout(FlowLayout.CENTER));
				{
					d.add(randomizeButton);
				}
				c.add(d);

				d = new JPanel(new GridLayout(1, 2));
				{
					JPanel e = new JPanel();
					{
						e.add(expandTreeButton);
					}
					d.add(e);

					e = new JPanel();
					{
						e.add(collapseTreeButton);
					}
					d.add(e);
				}
				c.add(d);

				// Séparateur horizontal
				c.add(new JSeparator(SwingConstants.HORIZONTAL));

				d = new JPanel(new GridLayout(2, 2));
				{
					JPanel e = new JPanel();
					{
						e.add(new JLabel("Noeud(s)"));
					}
					d.add(e);

					e = new JPanel();
					{
						e.add(addNodeCombo);
					}
					d.add(e);

					e = new JPanel();
					{
						e.add(new JLabel("Label"));
					}
					d.add(e);

					e = new JPanel();
					{
						e.add(newLabelNodeTextField);
					}
					d.add(e);
				}
				c.add(d);

				d = new JPanel(new FlowLayout(FlowLayout.CENTER));
				{
					d.add(addNodeButton);
				}
				c.add(d);

				// Séparateur horizontal
				c.add(new JSeparator(SwingConstants.HORIZONTAL));

				d = new JPanel(new GridLayout(1, 2));
				{
					JPanel e = new JPanel();
					{
						e.add(new JLabel("Noeud(s)"));
					}
					d.add(e);
					e = new JPanel();
					{
						e.add(deleteNodeCombo);
					}
					d.add(e);
				}
				c.add(d);

				d = new JPanel();
				{
					d.add(deleteNodeButton);
				}
				c.add(d);

				// Séparateur horizontal
				c.add(new JSeparator(SwingConstants.HORIZONTAL));

				d = new JPanel(new GridLayout(1, 2));
				{
					JPanel e = new JPanel(new FlowLayout(FlowLayout.CENTER));
					{
						e.add(ulehlaRadio);
					}
					d.add(e);

					e = new JPanel(new FlowLayout(FlowLayout.CENTER));
					{
						e.add(exhaustiveRadio);
					}
					d.add(e);
				}
				c.add(d);

				d = new JPanel();
				{
					d.add(searchWinningHitsButton);
				}
				c.add(d);

				d = new JPanel();
				{
					d.add(winningHitsLabel);
				}
				c.add(d);
			}
			b.add(c, BorderLayout.CENTER);
		}
		mainFrame.add(b, BorderLayout.EAST);
	}

	/**
	 * Méthode créant les actions des boutons.
	 * @since 1.0
	 * @version 1.0
	 */
	private void createController() {
		// Activation de la croix de fermeture de la fenêtre principale
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Bouton permettant d'ajouter un nouveau noeud en sectionnant son
		// parent ainsi que son nom
		addNodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Si un nom a bien été rentré, continuer
				if (!newLabelNodeTextField.getText().isEmpty()) {

					// Repasser la couleur de fond par défaut
					newLabelNodeTextField.setBackground(Color.WHITE);

					// Récupérer le nom (label) du nouveau noeud à ajouter
					// saisie par l'utilisateur
					String nodeLabel = newLabelNodeTextField.getText();

					// Création du model
					if (model == null) {
						if ((nodeLabel != null) && (!nodeLabel.isEmpty()) && (nodeLabel.matches("\\d+"))) {
							model = new Tree(new Node("-1", 0));
							jscroll.setViewportView(tree);
						}
					}

					// Liste (ArrayList) de tous les noeuds composant l'arbre
					List<Node> alOfNode = model.prefixeDepthCourse((Node) model.getRoot());

					// Père du nouveau noeud à ajouter
					Node fatherNode = null;

					// Parcours de tous les noeuds de l'arbre
					// 1 - Le noeud existe-t-il déja dans l'arbre ?
					// 2 - Récupérer le noeud père possédant bientôt un nouvel
					// enfant
					if ((nodeLabel != null) && (!nodeLabel.isEmpty()) && (nodeLabel.matches("\\d+"))) {

						// Le nouveau noeud à ajouter, est-il déjà prénsent dans
						// l'arbre ?
						boolean nodeIsAlreadyPresent = false;

						// Parcourir tous les noeuds de l'arbre
						for (Node n : alOfNode) {
							// Le noeud à ajouter existe-t-il déja dans l'arbre
							// ?
							if (n.getLabel().equals(nodeLabel)) {
								nodeIsAlreadyPresent = true;
							}

							// Récupérer le noeud père possédant bientôt un
							// nouvel enfant
							if (n.getLabel().equals(addNodeCombo.getSelectedItem())) {
								fatherNode = n;
							}
						}

						// Si le noeud à ajouter n'existe pas encore (dans
						// l'arbre), alors continuer
						if (!nodeIsAlreadyPresent) {
							// Si le père a bien été trouvé
							if (fatherNode != null) {
								// Ajouter le nouveau noeud au père
								fatherNode.addSuccessor(new Node(nodeLabel, fatherNode.getDepth() + 1));
								// Si le père n'a pas été trouvé dans l'arbre,
								// alors ajouter le nouvau noeud en tant que
								// nouvel arbre
							} else {
								model = new Tree(new Node(nodeLabel, 0));
								tree.setRootVisible(true);
							}
						}

					}

					// Actualiser et reconfigurer le model (clonage)
					model = (Tree) model.clone();
					tree.setModel(model);

					// Déployer l'arbre
					expandAll(tree);

					// Repeindre la fenêtre
					refresh();
					// Aucun nom a été rentré, alors une erreur à lieu
				} else {
					// Passer la couleur de fond en rouge
					newLabelNodeTextField.setBackground(Color.RED);

					// Afficher le popup avec le message d'erreur
					JOptionPane.showMessageDialog(mainFrame, "Label nouveau noeud vide !", "Erreur",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// Bouton permettant de supprimer un noeud
		deleteNodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Si la liste déroulante de suppression (ComboBox) n'est pas
				// vide
				if (deleteNodeCombo.getItemCount() != 0) {

					// Liste (ArrayList) de touts les noeuds composant l'arbre
					List<Node> alOfNode = model.suffixeDepthCourse((Node) model.getRoot());

					// Noeud à supprimer
					Node node = null;

					// Parcourir la liste de tous les noeuds de l'arbre
					for (Node n : alOfNode) {
						// Récupérer le noeud à supprimer
						if (n.getLabel().equals(deleteNodeCombo.getSelectedItem())) {
							node = n;
						}
					}

					// Remise à zéro du model
					model = new Tree(new Node("-1", 0, model.delete(node)));

					// Configuration du model
					tree.setModel(model);

					// Liste (ArrayList) de tous les noeuds composant l'arbre
					alOfNode = model.suffixeDepthCourse((Node) model.getRoot());

					// Masquer la racine de l'arbre (noeud fantome)
					tree.setRootVisible(false);

					// Si l'arbre ne possède plus aucun noeud, alors afficher un
					// message afin de prévenir l'utilisateur
					if (((Node) model.getRoot()).getSuccessorCount() == 0)
						JOptionPane.showMessageDialog(mainFrame, "Game Over", "Plus un seul noeud à jouer",
								JOptionPane.INFORMATION_MESSAGE);

					// Déployer l'arbre
					expandAll(tree);

					refresh();
					// Sinon afficher un message d'erreur
				} else {
					// Afficher le popup avec le message d'erreur
					JOptionPane.showMessageDialog(mainFrame, "Aucun noeud à supprimer !", "Erreur",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		searchWinningHitsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Si la méthode Ulehla est selectionnée
				if (ulehlaRadio.isSelected() && model != null) {
					String txt = new Ulehla(model).getWinninghits().toString();
					
					if (!txt.equals("[-1]")) {
						winningHitsLabel.setText(txt);
					}
					else {
						winningHitsLabel.setText("Aucun");
					}

					// Si la méthode Exhaustive est selectionnée
				} else if (exhaustiveRadio.isSelected() && model != null) {

					// Fenêtre popup
					int choice = JOptionPane.OK_OPTION;
					
					// Si l'arbre possède plus de 42 noeuds, demander à l'utilisateur confirmation
					if (model.getBarcode().getSize() > 42)
						choice = JOptionPane.showConfirmDialog(mainFrame,
								"Le temps d'execution de cette methode"
										+ " dépassera les 15 secondes, voulez vous continuer ? ",
								"Etes vous sur ?", JOptionPane.OK_CANCEL_OPTION);

					if (choice == JOptionPane.OK_OPTION) {
						winningHitsLabel.setText("Je cherche ...");
						Exaustive ex = new Exaustive(model);
						new SwingWorker<Void, Void>(){

							@Override
							protected Void doInBackground() throws Exception {
								ex.compute();
								return null;
							}
							@Override
							protected void done() {
							String txt = ex.getWinninghits().toString();
							
							if (!txt.equals("[-1]")) {
								winningHitsLabel.setText(txt);
							}
							else {
								winningHitsLabel.setText("Aucun");
							}								
							}
							
						}.execute();

						
						
					}
				}

				// Repeindre
				refresh();
			}
		});

		// Bouton permettant la création d'un nouvel arbre généré aléatoirement
		randomizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Vérifier la présence de valeurs pour la configuration
				// de la génération aléatoire de l'arbre
				if (!numberLeafRandomizeTextField.getText().isEmpty()
						&& !numberDepthRandomizeTextField.getText().isEmpty()) {

					// Repasser les couleurs de fonds par défaut
					numberLeafRandomizeTextField.setBackground(Color.WHITE);
					numberDepthRandomizeTextField.setBackground(Color.WHITE);
					
					int depth = Integer.valueOf(numberDepthRandomizeTextField.getText());
					int maxNode = Integer.valueOf(numberLeafRandomizeTextField.getText());

					// Générer un arbre aléatoirement
					Node root = new Node("1", 0);
					model = new RandomGenerateTree(root, depth, maxNode);
					tree = new JTree(model);

					// Récupérer le chemin absolu du projet
					String path = System.getProperty("user.dir");

					// Modifier les icones par défaut de l'arbre
					DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
					Icon closedIcon = new ImageIcon(path + "/pictures/closed.jpg");
					Icon openIcon = new ImageIcon(path + "/pictures/open.jpg");
					Icon leafIcon = new ImageIcon(path + "/pictures/leaf.jpg");
					renderer.setClosedIcon(closedIcon);
					renderer.setOpenIcon(openIcon);
					renderer.setLeafIcon(leafIcon);

					// Afficher l'arbre dans le panneau déroulant
					jscroll.setViewportView(tree);

					// Repeindre
					refresh();
				} else {
					// Passer la couleur de fond en rouge pour signaler le lieu
					// de l'erreur
					numberLeafRandomizeTextField.setBackground(Color.RED);
					numberDepthRandomizeTextField.setBackground(Color.RED);

					// Afficher le popup avec le message d'erreur
					JOptionPane.showMessageDialog(mainFrame,
							"Veuillez saisir des valeurs pour la génération aléatoire de l'arbre !", "Erreur",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

		// Bouton permettant de déployer l'arbre
		expandTreeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tree != null) {
					expandAll(tree);
					refresh();
				}
			}
		});

		// Bouton permettant de replier l'arbre
		collapseTreeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tree != null) {
					collapseAll(tree);
					refresh();
				}
			}
		});
	}

	/**
	 * Méthode actualisation l'affichage de la fenêtre.
	 * @since 1.0
	 * @version 1.0
	 */
	private void refresh() {

		// Actualiser les données des des deux listes déroulantes (ComboBox
		// Ajouter et Supprimer un noeud)
		if (model != null) {

			// Nettoyer les deux listes déroulantes
			addNodeCombo.removeAllItems();
			deleteNodeCombo.removeAllItems();

			// Liste (ArrayList) de tous les noeuds composant l'arbre
			List<Node> alOfNode = model.suffixeDepthCourse((Node) model.getRoot());

			// Liste vide (ArrayList) d'entiers
			List<Integer> alOfInteger = new ArrayList<Integer>();

			// Pourcourir tous les noeuds composant l'arbre afin de les placer
			// dans la liste d'entiers (converstion String en entiers))
			for (Node n : alOfNode) {
				int nombre = Integer.valueOf(n.getLabel());

				if (nombre != -1) {
					alOfInteger.add(nombre);
				}
			}

			// Trier la liste des noeuds d'entiers composant l'arbre
			Collections.sort(alOfInteger);

			// Parcourir la liste des noeuds d'entiers composant l'arbre afin de
			// les ajouter un par un aux liste déroulantes (Ajouter et
			// Supprimer)
			for (int i = 0; i < alOfInteger.size(); i++) {
				addNodeCombo.addItem(String.valueOf(alOfInteger.get(i)));
				deleteNodeCombo.addItem(String.valueOf(alOfInteger.get(i)));
			}

			// Repeindre l'arbre
			tree.repaint();
			// Si le model n'existe pas, alors vider les deux listes déroulantes
			// (Ajouter et Supprimer)
		} else {
			addNodeCombo.removeAllItems();
			deleteNodeCombo.removeAllItems();
		}
	}

	/**
	 * Méthode déployant la totalité de l'arbre.
	 * @param tree JTree
	 * @since 1.0
	 * @version 1.0
	 */
	private void expandAll(JTree tree) {
		for (int row = 0; row < tree.getRowCount(); row++) {
			tree.expandRow(row);
		}
	}

	/**
	 * Méthode repliant la totalité de l'arbre.
	 * @param tree JTree
	 * @since 1.0
	 * @version 1.0
	 */
	private void collapseAll(JTree tree) {
		for (int row = 1; row < tree.getRowCount(); row++) {
			tree.collapseRow(row);
		}
	}

	// POINT D'ENTREE (MAIN)

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ForestGame().display();
			}
		});
	}
}