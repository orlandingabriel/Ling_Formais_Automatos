package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AnalisadorView extends JFrame {

	private AnalisadorViewListener viewListener;

	private TextArea textAreaInput;
	private TextArea textAreaOutput;

	public AnalisadorView(AnalisadorViewListener viewListener) {
		this.viewListener = viewListener;

		setTitle("ANALISADOR");
		setSize(550, 450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		add(construirAreaInput(), BorderLayout.NORTH);
		add(construirBotoesAcao(), BorderLayout.CENTER);
		add(construirAreaOutput(), BorderLayout.SOUTH);

		setVisible(true);
	}

	private Component construirAreaInput() {
		textAreaInput = new TextArea();
		return textAreaInput;
	}

	private Component construirBotoesAcao() {
		JPanel jPanel = new JPanel();

		JButton jbuttonLexico = new JButton("Analisar Léxico");
		jbuttonLexico.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewListener.buttonAnalisarLexicoClick();
			}
		});
		
		JButton jbuttonSintatico = new JButton("Analisar Sintático");
		jbuttonSintatico.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewListener.buttonAnalisarSintaticoClick();
			}
		});
		
		JButton jbuttonSemantico = new JButton("Analisar Semantico");
		jbuttonSemantico.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewListener.buttonAnalisarSemanticoClick();
			}
		});

		jPanel.add(jbuttonLexico);
		jPanel.add(jbuttonSintatico);
		jPanel.add(jbuttonSemantico);
		return jPanel;
	}

	private Component construirAreaOutput() {
		textAreaOutput = new TextArea();
		textAreaOutput.setEditable(false);
		textAreaOutput.setBackground(Color.LIGHT_GRAY);
		textAreaOutput.setForeground(Color.BLUE);
		return textAreaOutput;
	}

	public String getInput() {
		return textAreaInput.getText();
	}

	public void setOutput(String output) {
		textAreaOutput.setText(output);
	}
	
	public void setErro(String erro) {
		textAreaOutput.setForeground(Color.RED);
		textAreaOutput.setText(erro);
	}

	public void appendOutput(String output) {
		textAreaOutput.append(output);
		textAreaOutput.append("\n");
	}

	public void clearOutput() {
		textAreaOutput.setForeground(Color.BLUE);
		setOutput("");
	}

}
