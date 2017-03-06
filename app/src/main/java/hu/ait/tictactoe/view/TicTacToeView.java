package hu.ait.tictactoe.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import hu.ait.tictactoe.MainActivity;
import hu.ait.tictactoe.R;
import hu.ait.tictactoe.model.TicTacToeModel;

public class TicTacToeView extends View {

    private Paint paintBg;
    private Paint paintLine;
    private Paint paintLineSecond;
    private Paint paintText;
    private short whoWon;
    private Bitmap bitmapBg;
    private int counter;

    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        counter = 0;
        whoWon = 0;

        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintLineSecond = new Paint();
        paintLineSecond.setColor(Color.RED);
        paintLineSecond.setStyle(Paint.Style.STROKE);
        paintLineSecond.setStrokeWidth(5);

        paintText= new Paint();
        paintText.setColor(Color.GREEN);



        bitmapBg = BitmapFactory.decodeResource(getResources(),R.drawable.grass_background);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmapBg = Bitmap.createScaledBitmap(bitmapBg, getWidth(), getHeight(), false);

        paintText.setTextSize(getHeight()/8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(),
                paintBg);

        canvas.drawBitmap(
                bitmapBg,
                0,0,
                null);


        drawGameGrid(canvas);

        drawPlayers(canvas);


        if (whoWon == 1) {
            paintText.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Circles won!", (2*getWidth())/3, getHeight()/2 ,paintText); //x=300,y=300
            paintText.setTextSize(getHeight()/13);
            canvas.drawText("Click anywhere to restart", (getWidth())/2, getHeight()-100 ,paintText); //x=300,y=300
            counter = 0;
            whoWon = 0;
        }
        if (whoWon == 2) {
            paintText.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Crosses won!", (2*getWidth())/3, getHeight()/2 ,paintText); //x=300,y=300
            paintText.setTextSize(getHeight()/13);
            canvas.drawText("Click anywhere to restart", (getWidth())/2, getHeight()-100 ,paintText); //x=300,y=300
            counter = 0;
            whoWon = 0;
        }
        if (whoWon == 3) {
            paintText.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("It's a tie!", (2*getWidth())/3, getHeight()/2 ,paintText); //x=300,y=300
            paintText.setTextSize(getHeight()/13);
            canvas.drawText("Click anywhere to restart", (getWidth())/2, getHeight()-100 ,paintText); //x=300,y=300
            whoWon = 0;
            counter = 0;
        }
    }


    private void drawCrossesWinner(Canvas canvas){
        canvas.drawText("Crosses won!", 0, 10000, paintText);
    }

    private void drawCriclesWinner(Canvas canvas){
        canvas.drawText("Cricles won!", 0, 10000, paintText);
    }


    private void drawPlayers(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TicTacToeModel.getInstance().getField(i,j) == TicTacToeModel.CIRCLE) {

                    // draw a circle at the center of the field

                    // X coordinate: left side of the square + half width of the square
                    float centerX = i * getWidth() / 3 + getWidth() / 6;
                    float centerY = j * getHeight() / 3 + getHeight() / 6;
                    int radius = getHeight() / 6 - 2;

                    canvas.drawCircle(centerX, centerY, radius, paintLineSecond);

                } else if (TicTacToeModel.getInstance().getField(i,j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(i * getWidth() / 3, j * getHeight() / 3,
                            (i + 1) * getWidth() / 3,
                            (j + 1) * getHeight() / 3, paintLine);

                    canvas.drawLine((i + 1) * getWidth() / 3, j * getHeight() / 3,
                            i * getWidth() / 3, (j + 1) * getHeight() / 3, paintLine);

                }
            }
        }
    }

    private void drawGameGrid(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // two horizontal lines
        canvas.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 3, getWidth(),
                2 * getHeight() / 3, paintLine);

        // two vertical lines
        canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 3, 0, 2 * getWidth() / 3, getHeight(),
                paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int tX = ((int)event.getX()) / (getWidth()/3);
            int tY = ((int)event.getY()) / (getHeight()/3);

            if (TicTacToeModel.getInstance().getField(tX, tY) == TicTacToeModel.EMPTY) {
                counter++;
                TicTacToeModel.getInstance().setField(tX, tY,
                        TicTacToeModel.getInstance().getNextPlayer());
                TicTacToeModel.getInstance().changeNextPlayer();

                invalidate();

                if (hasWon() == 0) {
                    String next = "O";
                    if (TicTacToeModel.getInstance().getNextPlayer() == TicTacToeModel.CROSS) {
                        next = "X";
                    }
                    ((MainActivity) getContext()).setMessage(
                            getResources().getString(R.string.next_player, next)
                    );
                }
                else if (hasWon() == 1) {
                    whoWon = 1;
                    resetGame();
                }
                else if (hasWon() == 2) {
                    whoWon = 2;
                    resetGame();
                }
                if (counter == 9) {
                    whoWon = 3;
                    resetGame();
                }
            }
        }

        return true;
    }


    private short hasWon() {
        return TicTacToeModel.getInstance().checkWinner();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    public void resetGame() {
        TicTacToeModel.getInstance().resetModel();
        invalidate();
    }

}