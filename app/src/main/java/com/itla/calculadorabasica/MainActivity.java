package com.itla.calculadorabasica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private Expression expression = new Expression();
	private final String NAN = "NaN";

	private ConstraintLayout constraintLayout;
	private TextView txtValue;
	private TextView txtAmount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtValue = findViewById(R.id.txtValue);
		txtAmount = findViewById(R.id.txtAmount);
		constraintLayout = findViewById(R.id.main);
		txtValue.setText("");
		txtAmount.setText("");

		setListenerToButton(constraintLayout.getChildCount());

	}

	private void setListenerToButton(int childCount) {

		for (int i = 0; i < childCount; i++) {
			View child = constraintLayout.getChildAt(i);

			if (child instanceof Button) {
				child.setOnClickListener(this);
			}

		}

	}


	@Override
	public void onClick(View v) {
		Button button = (Button) v;

		String literal = button.getText().toString().replace("=", "");
		String operation = txtValue.getText().toString().replace(" X ", " * ");

		prepareExpression(literal, operation);

	}

	private void prepareExpression(String literal, String operation) {

		if ("C".equals(literal)) {

			txtValue.setText("");
			txtAmount.setText("");

		} else {

			if (!operation.isEmpty() && evaluatePrecedingNumber(literal)) {
				return;
			}

			txtValue.setText(txtValue.getText().toString().concat(literal));

			evaluateExpression(operation.concat(literal));

		}
	}

	private boolean evaluatePrecedingNumber(String literal) {

		String character = txtValue.getText().toString();
		character = character.substring(character.length() - 1, character.length());

		String lastCharacter = calculate(character)+"";
		String lastLiteral = calculate(literal) + "";

		return (NAN.equals(lastCharacter) && NAN.equals(lastLiteral));
	}

	private void evaluateExpression(String expression) {

		String oldValue = txtAmount.getText().toString();
		double result = calculate(expression);

		if ((result + "").equals(NAN)) {
			txtAmount.setText(oldValue);
		} else {
			txtAmount.setText(result + "");
		}
	}

	private double calculate(String value) {
		expression.setExpressionString(value.trim());
		return expression.calculate();
	}

}
