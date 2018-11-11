package com.eclipsesource.rap.examples.maven;

import java.util.Arrays;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.client.service.ClientFileLoader;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;
import org.eclipse.rap.rwt.widgets.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

public class DemoApplication implements EntryPoint {

	private Composite dialogContent;
	private Combo dialogCcombo;

	public int createUI() {
		Display display = new Display();
		Shell mainShell = new Shell(display, SWT.TITLE);
		mainShell.setText("RAP Angural PoC");
		mainShell.setMaximized(true);
		mainShell.setLayout(new GridLayout(1, false));

		ClientFileLoader loader = RWT.getClient().getService(ClientFileLoader.class);
		loader.requireJs(RWT.getResourceManager().getLocation("main.js"));
		loader.requireJs(RWT.getResourceManager().getLocation("polyfills.js"));
		loader.requireJs(RWT.getResourceManager().getLocation("runtime.js"));
		loader.requireJs(RWT.getResourceManager().getLocation("styles.js"));
		loader.requireJs(RWT.getResourceManager().getLocation("vendor.js"));

		createSelectionComposite(mainShell);
		dialogContent = new Composite(mainShell, SWT.NORMAL);
		dialogContent.setLayout(new FillLayout());
		new Label(dialogContent, SWT.NORMAL).setText("Dialog area");

		mainShell.layout();
		mainShell.open();
		mainShell.layout();
		return 0;
	}

	private void setEntryId(Widget widget, String id) {
		String widgetId = WidgetUtil.getId(widget);
		exec("rap.getObject('", widgetId, "').", "$el", ".attr('si-entry-id', '", id + "' );");
	}

	private void exec(String... strings) {
		StringBuilder builder = new StringBuilder();
		builder.append("try {");

		for (String str : strings) {
			builder.append(str);
		}

		builder.append("} catch (e) {}");

		JavaScriptExecutor executor = RWT.getClient().getService(JavaScriptExecutor.class);
		executor.execute(builder.toString());
	}

	private void createSelectionComposite(Composite parent) {
		Composite cmp = new Composite(parent, SWT.NORMAL);
		cmp.setLayout(new GridLayout(2, false));
		Label l = new Label(cmp, SWT.NORMAL);
		l.setText("Dialog");
		dialogCcombo = new Combo(cmp, SWT.NORMAL);
		dialogCcombo.add("Person");
		dialogCcombo.add("Address");
		dialogCcombo.add("Contract");
		dialogCcombo.addSelectionListener(getDispatchDialogListener());
	}

	private void createPersonDialog() {
		Arrays.stream(dialogContent.getChildren()).forEach(c -> c.dispose());
		dialogContent.setLayout(new GridLayout(2, false));
		new Label(dialogContent, SWT.NORMAL).setText("Name");
		new Text(dialogContent, SWT.BORDER).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(dialogContent, SWT.NORMAL).setText("Firstname");
		new Text(dialogContent, SWT.BORDER).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Display.getCurrent().asyncExec(new Runnable() {

			@Override
			public void run() {
				dialogContent.getParent().layout();
			}
		});
	}

	private void createAddressDialog() {
		Arrays.stream(dialogContent.getChildren()).forEach(c -> c.dispose());
		dialogContent.setLayout(new GridLayout(2, false));
		new Label(dialogContent, SWT.NORMAL).setText("Postal code");
		new Text(dialogContent, SWT.BORDER).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(dialogContent, SWT.NORMAL).setText("City");
		new Text(dialogContent, SWT.BORDER).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(dialogContent, SWT.NORMAL).setText("Street");
		new Text(dialogContent, SWT.BORDER).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Display.getCurrent().asyncExec(new Runnable() {

			@Override
			public void run() {
				dialogContent.getParent().layout();
			}
		});
	}

	private void createContractDialog() {
		JavaScriptExecutor executor = RWT.getClient().getService(JavaScriptExecutor.class);
		executor.execute("console.log('Hello')");
		executor.execute("console.log(window.bootstrapComponentContract)");
		executor.execute("window.bootstrapComponentContract()");

		Arrays.stream(dialogContent.getChildren()).forEach(c -> c.dispose());
		dialogContent.setLayout(new GridLayout(1, false));
		Composite container1 = new Composite(dialogContent, SWT.NORMAL);
		setEntryId(container1, "component-contract");
		Display.getCurrent().asyncExec(new Runnable() {

			@Override
			public void run() {
				dialogContent.getParent().layout();
			}
		});
	}

	private SelectionListener getDispatchDialogListener() {
		return new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				System.out.println(dialogCcombo.getText());
				switch (dialogCcombo.getText()) {
				case "Person":
					createPersonDialog();
					break;
				case "Address":
					createAddressDialog();
					break;
				case "Contract":
					createContractDialog();
					break;

				default:
					break;
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		};
	}

}
