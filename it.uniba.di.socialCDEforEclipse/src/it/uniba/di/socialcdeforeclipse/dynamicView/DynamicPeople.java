package it.uniba.di.socialcdeforeclipse.dynamicView;

import it.uniba.di.socialcdeforeclipse.action.ActionGeneral;
import it.uniba.di.socialcdeforeclipse.controller.Controller;
import it.uniba.di.socialcdeforeclipse.sharedLibrary.WUser;
import it.uniba.di.socialcdeforeclipse.views.ButtonPerson;
import it.uniba.di.socialcdeforeclipse.views.Panel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class DynamicPeople implements Panel{

	
	
	private ArrayList<Control> controlli;
	 
	private Composite combo;
	 
	
	
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0,
		image.getBounds().width, image.getBounds().height,
		0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
		}
	
	public Image getImageStream(InputStream stream)
	{
		return  new Image(Controller.getWindow().getDisplay(),stream); 
	}
	
	@Override
	public void inizialize(Composite panel2) {
		// TODO Auto-generated method stub
		
		 
		
		
	
	
		
		
		
		GridData grid = new GridData(); 
		
		
		controlli = new ArrayList<Control>();
		Listener azioni = new ActionGeneral();
		panel2.setLayout(new GridLayout(3, true));
		
		
		 
		Label labelSuggestion = new Label(panel2, SWT.NONE); 
		labelSuggestion.setText("Suggestions:"); 
		labelSuggestion.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 14, SWT.BOLD )); 
		grid = new GridData(); 
		grid.horizontalSpan = 3; 
		grid.grabExcessHorizontalSpace = true;
		grid.horizontalAlignment = GridData.FILL; 
		labelSuggestion.setLayoutData(grid); 
		controlli.add(labelSuggestion);
		
		WUser[] suggestion = Controller.getProxy().GetSuggestedFriends(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		//WUser[] suggestion = Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword());
		if(suggestion.length == 0)
		{
			Label labelsuggestionText = new Label(panel2, SWT.WRAP); 
			labelsuggestionText.setText("We have no suggestion for you.\n Please try again soon."); 
			grid = new GridData(); 
			grid.horizontalSpan = 3; 
			grid.grabExcessHorizontalSpace = true;
			grid.horizontalAlignment = GridData.FILL; 
			 
			
			labelsuggestionText.setLayoutData(grid);
			controlli.add(labelsuggestionText);
			
		}
		else
		{
			System.out.println("Persone suggerite " + suggestion.length);
			 
			
			for (int i = 0; i < suggestion.length ; i++) {

				 combo = new Composite(panel2, SWT.None); 
				 combo.setLayout(new GridLayout(1,false)); 
				combo.setLayoutData(new GridData(100, 120)); 
				
				
				ButtonPerson person = new ButtonPerson(combo, SWT.None); 
				person.setxCoordinate(0); 
				 
				person.setyCoordinate(0); 
				person.setWidth(100);
				person.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD )); 
				person.setHeight(120); 
			
				person.setDefaultColors(new Color(Controller.getWindow().getDisplay(), 81,179,225), new Color(Controller.getWindow().getDisplay(), 81,179,225), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
				person.setHoverColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				person.setClickedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				person.setSelectedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				try {
					System.out.println("Immagine link " + suggestion[i].Avatar);
					System.out.println("Immagine di " + suggestion[i].Username);
					Image imageProposed = getImageStream(new URL(suggestion[i].Avatar).openStream()); 
					System.out.println("Immagine dim " + imageProposed.getBounds() + " di " + suggestion[i].Username); 
					person.setImage( resize(imageProposed,80,80));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					person.setImage(resize(getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png")),80,80)); 
					try {
						this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png").close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					//e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					person.setImage(resize(getImageStream(this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png")),80,80));
					try {
						this.getClass().getClassLoader().getResourceAsStream("images/DefaultAvatar.png").close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}
				person.setText(suggestion[i].Username);
				 
				controlli.add(combo);
				
				 
			}
		}
		
		Label labelFollowings = new Label(panel2, SWT.NONE); 
		labelFollowings.setText("Followings:"); 
		labelFollowings.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 14, SWT.BOLD )); 
		grid = new GridData(); 
		grid.horizontalSpan = 3; 
		grid.grabExcessHorizontalSpace = true; 
		grid.horizontalAlignment = GridData.FILL; 
		labelFollowings.setLayoutData(grid); 
		controlli.add(labelFollowings);
		
		WUser[] following = Controller.getProxy().GetFollowings(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		if(following.length == 0)
		{
			Label labelFollowingText = new Label(panel2, SWT.WRAP); 
			labelFollowingText.setText("You are following no one."); 
			grid = new GridData(); 
			grid.horizontalSpan = 3; 
			labelFollowingText.setLayoutData(grid);
			controlli.add(labelFollowingText);
			
		}
		else
		{
			
			//System.out.println("Persone suggerite " + following.length);
			
			for (int i = 0; i < following.length; i++) {

				 combo = new Composite(panel2, SWT.None); 
				 combo.setLayout(new GridLayout(1,false)); 
					combo.setLayoutData(new GridData(100, 120));
				ButtonPerson person = new ButtonPerson(combo, SWT.None); 
				person.setxCoordinate(0); 
				person.setyCoordinate(0); 
				person.setWidth(100);
				person.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD )); 
				person.setHeight(120); 
			
				person.setDefaultColors(new Color(Controller.getWindow().getDisplay(), 81,179,225), new Color(Controller.getWindow().getDisplay(), 81,179,225), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
				person.setHoverColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				person.setClickedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				person.setSelectedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				try {
					person.setImage(getImageStream(new URL(following[i].Avatar).openStream()));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				person.setText(following[i].Username);
				controlli.add(combo);
			}
		}
		
		Label labelFollowers = new Label(panel2, SWT.NONE); 
		labelFollowers.setText("Followers:"); 
		labelFollowers.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 14, SWT.BOLD )); 
		grid.horizontalSpan = 3; 
		grid.grabExcessHorizontalSpace = true;
		grid.horizontalAlignment = GridData.FILL; 
		labelFollowers.setLayoutData(grid); 
		controlli.add(labelFollowers);
		
		WUser[] followers = Controller.getProxy().GetFollowers(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword()); 
		
		if(followers.length == 0)
		{
			Label labelFollowersText = new Label(panel2, SWT.WRAP); 
			labelFollowersText.setText("No one is following you."); 
			grid = new GridData(); 
			grid.horizontalSpan = 3; 
			labelFollowersText.setLayoutData(grid);
			controlli.add(labelFollowersText);
			
		}
		else
		{
			
		//	System.out.println("Persone suggerite " + followers.length);
			
			for (int i = 0; i < followers.length; i++) {

				 combo = new Composite(panel2, SWT.None); 
				 combo.setLayout(new GridLayout(1,false)); 
				 combo.setLayoutData(new GridData(100, 120));
				
				ButtonPerson person = new ButtonPerson(combo, SWT.None); 
				person.setxCoordinate(0); 
				person.setyCoordinate(0); 
				person.setWidth(100);
				person.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD )); 
				person.setHeight(120); 
			
				person.setDefaultColors(new Color(Controller.getWindow().getDisplay(), 81,179,225), new Color(Controller.getWindow().getDisplay(), 81,179,225), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
				person.setHoverColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				person.setClickedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				person.setSelectedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				try {
					person.setImage(resize(getImageStream(new URL(followers[i].Avatar).openStream()),80,80));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				person.setText(followers[i].Username);
				controlli.add(combo);
			}
		}
	
		Label labelHidden = new Label(panel2, SWT.NONE); 
		labelHidden.setText("Hidden:"); 
		labelHidden.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 14, SWT.BOLD )); 
		grid.horizontalSpan = 3; 
		labelHidden.setLayoutData(grid); 
		controlli.add(labelHidden);	
		
	WUser[]	hiddenUsers = Controller.getProxy().GetHiddenUsers(Controller.getCurrentUser().Username, Controller.getCurrentUserPassword());
	
	if(hiddenUsers.length == 0)
	{
		Label labelHiddenUsersText = new Label(panel2, SWT.WRAP); 
		labelHiddenUsersText.setText("You have hidden no one."); 
		grid = new GridData(); 
		grid.horizontalSpan = 3; 
		labelHiddenUsersText.setLayoutData(grid);
		controlli.add(labelHiddenUsersText);
		
	}
	else
	{
		
		//System.out.println("Persone nascoste " + hiddenUsers.length);
		
		for (int i = 0; i < hiddenUsers.length; i++) {

			 combo = new Composite(panel2, SWT.None); 
			 combo.setLayout(new GridLayout(1,false)); 
			grid = new GridData(); 
			grid.widthHint = 100;
			grid.heightHint = 120;  
			combo.setLayoutData(grid);
			
			ButtonPerson person = new ButtonPerson(combo, SWT.None); 
			person.setxCoordinate(0); 
			person.setyCoordinate(0); 
			person.setWidth(100);
			person.setFont(new Font(Controller.getWindow().getDisplay(),"Calibri", 10, SWT.BOLD )); 
			person.setHeight(120); 
		
			person.setDefaultColors(new Color(Controller.getWindow().getDisplay(), 81,179,225), new Color(Controller.getWindow().getDisplay(), 81,179,225), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)); 
			person.setHoverColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			person.setClickedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			person.setSelectedColors(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), null, Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			try {
				person.setImage(resize(getImageStream(new URL(hiddenUsers[i].Avatar).openStream()),80,80));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			person.setText(hiddenUsers[i].Username);
			controlli.add(combo);
		}
	}
	
	panel2.redraw(); 
	
	
	
	}

	@Override
	public void dispose(Composite panel) {
		// TODO Auto-generated method stub
		
		for(int i=0; i < controlli.size();i++)
		{
		 controlli.get(i).dispose(); 
			
		}
		panel.setLayout(null); 
		
	}


	@Override
	public HashMap<String, String> getInput() {
		// TODO Auto-generated method stub
		return null;
	}

}