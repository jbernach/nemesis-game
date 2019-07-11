package com.superjose128.nemesis.core.powerup;

import com.superjose128.nemesis.core.NemesisGame;
import playn.core.*;
import tripleplay.util.Colors;

import java.util.ArrayList;


public class WeaponBoard {
    public final NemesisGame game;
    private final Font font;
    private final TextFormat textFormat;

    private ArrayList<WeaponBoardSlot> slots = new ArrayList<>();
    private int currentSlotIndex = -1; // -1 means none selected

    private int lives = 0;
    private int score = 0;

    private float width = 300;
    private float height = 20;

    private int rows = 1;
    private int maxColsRow = 1;

    public WeaponBoard(NemesisGame game, float width, float height, int maxColsPerRow) {
        this.game = game;
        this.setMaxColsRow(maxColsPerRow);
        this.width = width;
        this.height = height;
        this.font =  new Font("Nemesis", Font.Style.PLAIN, 28);
        this.textFormat = new TextFormat(font,false);
    }

    public void paint(Surface surface) {
        paintLivesAndScore(surface);

        this.rows = Math.max(1,(int)Math.ceil(slots.size()/(float)getMaxColsRow()));

        Object[] arr = slots.toArray();
        int i = 1, r = 0, c = 0;
        int numCols = getCols();

        for(Object slot:arr){
            r = (int) Math.ceil(i/(float)numCols) - 1;
            c = (i - 1) % numCols;
            i++;
            WeaponBoardSlot ws = (WeaponBoardSlot)slot;
            ws.row = r;
            ws.col = c;
            ws.paint(surface);
        }

        paintCurrentSlot(surface);
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public float getChooserWidth(){
        return width/2;
    }

    public float getChooserHeight(){
        return height/2;
    }

    public float getChooserHMargin(){
        return 50;
    }

    public int getRows() {
        return rows;
    }

    public int getMaxColsRow() {
        return maxColsRow;
    }

    public int getCols(){
        return Math.min(maxColsRow, slots.size());
    }

    public void setMaxColsRow(int cols) {
        this.maxColsRow = cols;
    }

    public void addSlot(WeaponBoardSlot slot){
        slots.add(slot);
    }

    public void inserBefore(WeaponBoardSlot slot, String name){

    }

    public PowerUp getReadyPowerUp(){
        if(currentSlotIndex < 0) return null;

        WeaponBoardSlot slot = slots.get(currentSlotIndex);
        if(slot.isDisabled()){
            return null;
        }else{
            return slot.getPowerup();
        }
    }

    public void selectSlot(int index){
        if(currentSlotIndex >= 0){
            slots.get(currentSlotIndex).setCurrent(false);
        }

        currentSlotIndex = index;
        if(currentSlotIndex >= 0){
            slots.get(currentSlotIndex).setCurrent(true);
        }

    }

    protected void onWeaponArm(PowerUp value){
        Object[] arr = slots.toArray();

        for(int i = 0;i < arr.length;i++){
            WeaponBoardSlot ws = (WeaponBoardSlot)arr[i];

            if(ws.getName().equalsIgnoreCase(value.getName())){
                game.plat.log().debug("level"+value.getLevel() + " max:"+value.getMaxLevels());
                if(value.getLevel() == value.getMaxLevels()){
                    ws.setDisabled(true);
                }
            }else{
                String[] excludes = value.getExcludes();
                if (excludes != null){
                    for(int j = 0;j < excludes.length;j++){
                        if(excludes[j].equalsIgnoreCase(ws.getName())){
                            ws.setDisabled(true);
                        }
                    }
                }
            }
        }
    }

    private void paintCurrentSlot(Surface surface) {
        float wAvail = getWidth() - getChooserWidth();
        float w = wAvail/3;
        float h = getHeight()/2;
        float offX = getWidth() - w - 40;//getChooserWidth() + (wAvail - w)/2;
        float offY = 0;

        Canvas canvas = game.plat.graphics().createCanvas(w, h);
	
		/*canvas.setFillColor(Colors.BLACK);
		canvas.fillRect(0, 0, w, h);*/

        WeaponBoardSlot slot = null;
        if(currentSlotIndex >= 0){
            slot = slots.get(currentSlotIndex);
        }

        if(slot == null){
            canvas.setFillColor(Colors.BLUE);
            canvas.fillRect(5, 5, w-10, h-10);

            canvas.setFillColor(Colors.YELLOW);
            TextLayout textLayout = game.plat.graphics().layoutText("ENABLE", textFormat);
            canvas.fillText(textLayout, (w - textLayout.size.width())/2, 8);

            canvas.setStrokeWidth(2);
            canvas.drawLine(10, 10, 35, 10);
            canvas.drawLine(10, 10, 10, h-10);
            canvas.drawLine(10, h-10, 35, h-10);
            canvas.drawLine(w-10, 10, w-35, 10);
            canvas.drawLine(w-10, 10, w-10, h-10);
            canvas.drawLine(w-10, h-10, w-35, h-10);
        }else if(slot.isDisabled()){
            canvas.setStrokeWidth(2);
            canvas.setStrokeColor(Colors.RED);
            canvas.strokeRect(5, 5, w - 10, h - 10);
        }else{
            canvas.setFillColor(Colors.YELLOW);
            canvas.fillRect(5, 5, w-10, h-10);
            canvas.setStrokeColor(Colors.WHITE);
            canvas.setStrokeWidth(2);
            canvas.drawLine(5, 5, w-5, 5);
            canvas.drawLine(5, h-5, w-5, h-5);

            canvas.setFillColor(Colors.BLUE);
            TextLayout textLayout = game.plat.graphics().layoutText(slot.getName(), textFormat);
            canvas.fillText(textLayout, (w - textLayout.size.width())/2, 8);

        }

        surface.draw(canvas.snapshot().tile(), offX, offY);
    }

    private void paintLivesAndScore(Surface surface) {
        float w = getWidth();
        float h = getHeight();

        Canvas canvas = game.plat.graphics().createCanvas(w, h);

        canvas.setFillColor(Colors.BLACK);
        canvas.fillRect(0, 0, w, h);

        canvas.setFillColor(Colors.WHITE);
        TextLayout textLayout = game.plat.graphics().layoutText("" + this.getLives() + " LIVES", textFormat);
        canvas.fillText(textLayout, getChooserHMargin(), getChooserHeight() + 8);
        textLayout = game.plat.graphics().layoutText("" + this.getScore(), textFormat);
        canvas.fillText(textLayout, getChooserHMargin() + getChooserWidth()/3, getChooserHeight() + 8);

        surface.draw(canvas.snapshot().tile(), 0, 0);
    }


    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
