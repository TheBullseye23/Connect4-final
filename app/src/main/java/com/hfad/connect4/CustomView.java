package com.hfad.connect4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CustomView extends View {

    private Canvas mCanvas1;      //FOR DRAWING DISCS(TRANSPARENT)
    private Canvas mCanvas2;      // FOR DRAWING BOARD INITIALLY
    private Canvas mCanvas3;
    private Bitmap mBitmap1;        // FOR DRAWING DISCS(TRANSPARENT)
    private Bitmap mBitmap2;        // FOR DRAWING BOARD
    private Bitmap mBitmap3;   //After overlapping the two bitmaps , inside onDraw
    private Paint  mPaint1;        // for drawing the board
    private Paint  kPaint;
    private Paint  dPaint;
    private Paint  zPaint;             //for filling undo rectangle
    private Paint  txtPaint;
    private int    count=0;         //for maintaining counter
    private float  mWidth;
    private float  mHeigth;
    private float  W;
    private float  H;

    private Rect mRect;             // Board's rectangle
    private Rect undorect;

    private int[][] a = new int[7][6];
    private int[][][] c = new int [21][2][2];
    private float radius;
    private int move;
    private int player;

    private int    BackgroundColor;
    private int    BoardColor;
    private int    undorectcolor;





    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void init()
    {   dPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        kPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        zPaint = new Paint();
        txtPaint = new Paint();
        txtPaint.setColor(Color.BLACK);
        txtPaint.setTextSize(50);
        zPaint.setStyle(Paint.Style.FILL);
        zPaint.setColor(Color.GREEN);
        BackgroundColor = Color.parseColor("#202641");
        BoardColor = Color.parseColor("#229ED6");
        mPaint1.setColor(BoardColor);
        kPaint.setColor(Color.WHITE);
        dPaint.setColor(Color.WHITE);
        mRect = new Rect();
        undorect = new Rect();
        move=0;
        player=0;

    }

    @Override
    protected void onSizeChanged(int mWidth, int mHeigth, int oldw, int oldh) {
        this.mWidth=mWidth;
        this.mHeigth=mHeigth;
        W=mWidth/9;
        H = mHeigth/12;
        radius=W/2;
        mRect.set((int)(1*W),(int)(3*H),(int) (8*W), (int)(9*H));

        mBitmap1 = Bitmap.createBitmap(mWidth,mHeigth, Bitmap.Config.ARGB_8888);
        mCanvas1 = new Canvas(mBitmap1);

        mBitmap2 = Bitmap.createBitmap((int)mWidth, (int) mHeigth, Bitmap.Config.ARGB_8888);
        mCanvas2 = new Canvas(mBitmap2);

        undorect.set((int)(3*W),30,(int)(6*W),(int)H);

        super.onSizeChanged(mWidth, mHeigth, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(count==0)
        {
              drawRect();

        }
        mBitmap3=merge(mBitmap1,mBitmap2);                          // 1st argument draws over 2nd argument
        canvas.drawBitmap(mBitmap3,0,0,null);
        result();
    }

    private void result() {

        int player1=4;
        int f=0;
        for (int j = 0; j<3; j++ ){
            for (int i = 0; i<7; i++){
                if (this.a[i][j] == player1 && this.a[i][j+1] == player1 && this.a[i][j+2] == player1 && this.a[i][j+3] == player1){
                    {f=1;
                        Toast.makeText(getContext(),"Yellow won",Toast.LENGTH_LONG).show();}
                }
            }
        }

        for (int i = 0; i<4 ; i++ ){
            for (int j = 0; j<6; j++){
                if (this.a[i][j] == player1 && this.a[i+1][j] == player1 && this.a[i+2][j] == player1 && this.a[i+3][j] == player1){
                    {f=1;
                        Toast.makeText(getContext(),"Yellow won",Toast.LENGTH_LONG).show();}
                }
            }
        }

        for (int i=3; i<6; i++){
            for (int j=0; j<2; j++){
                if (this.a[i][j] == player1 && this.a[i-1][j+1] == player1 && this.a[i-2][j+2] == player1 && this.a[i-3][j+3] == player1)
                {f=1;
                    Toast.makeText(getContext(),"Yellow won",Toast.LENGTH_LONG).show();}
            }
        }

        for (int i=3; i<7; i++){
            for (int j=3; j<6; j++){
                if (this.a[i][j] == player1 && this.a[i-1][j-1] == player1 && this.a[i-2][j-2] == player1 && this.a[i-3][j-3] == player1)
                {f=1;
                    Toast.makeText(getContext(),"Yellow won",Toast.LENGTH_LONG).show();}
            }
        }

        if(f==0)
        {
            player1=3;
            for (int j = 0; j<3; j++ ){
                for (int i = 0; i<7; i++){
                    if (this.a[i][j] == player1 && this.a[i][j+1] == player1 && this.a[i][j+2] == player1 && this.a[i][j+3] == player1){
                        Toast.makeText(getContext(),"Red won",Toast.LENGTH_LONG).show();
                    }
                }
            }

            for (int i = 0; i<4 ; i++ ){
                for (int j = 0; j<6; j++){
                    if (this.a[i][j] == player1 && this.a[i+1][j] == player1 && this.a[i+2][j] == player1 && this.a[i+3][j] == player1){
                        Toast.makeText(getContext(),"Red won",Toast.LENGTH_LONG).show();
                    }
                }
            }

            for (int i=3; i<6; i++){
                for (int j=0; j<2; j++){
                    if (this.a[i][j] == player1 && this.a[i-1][j+1] == player1 && this.a[i-2][j+2] == player1 && this.a[i-3][j+3] == player1)
                        Toast.makeText(getContext(),"Red won",Toast.LENGTH_LONG).show();
                }
            }

            for (int i=3; i<7; i++){
                for (int j=3; j<6; j++){
                    if (this.a[i][j] == player1 && this.a[i-1][j-1] == player1 && this.a[i-2][j-2] == player1 && this.a[i-3][j-3] == player1)
                        Toast.makeText(getContext(),"Red won",Toast.LENGTH_LONG).show();
                }
            }


        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            float x = event.getX();
            float y = event.getY();
            if(x>W&&x<(8*W)&&y>H)
            {   count++;
                drawDisc(x);
            invalidate();}
            else if(x>3*W&&x<(6*W)&&y<H&&y>30)
            {   if(count>0)
                {
                redo();
                invalidate();}
            }
           else;
        }
        return true;
    }

    private void redo() {
        move=count/2;
        player=count%2;
        int i = c[move][player][0];
        int j = c[move][player][1];

        a[i][j]=0;
        dPaint.setColor(Color.WHITE);
        mCanvas1.drawCircle(radius + (i + 1) * W,(8-j) * H+radius , radius, dPaint);
        count--;
    }

    private Bitmap merge(Bitmap mBitmap1, Bitmap mBitmap2) {
        if(mBitmap3==null)
            {mBitmap3 = Bitmap.createBitmap((int)mWidth, (int) mHeigth, Bitmap.Config.ARGB_8888);
             mCanvas3 = new Canvas(mBitmap3);}

        mCanvas3.drawBitmap(mBitmap2,new Matrix(),null);
        mCanvas3.drawBitmap(mBitmap1,new Matrix(),null);
        return mBitmap3;
    }

    private void drawDisc(float x)
    {
        int player1;

        if(mBitmap1==null)
        {
            mBitmap1 = Bitmap.createBitmap((int)mWidth, (int) mHeigth, Bitmap.Config.ARGB_8888);
            mCanvas1 = new Canvas(mBitmap1);
            mCanvas1.drawColor(Color.TRANSPARENT);
        }
        if(count%2==0)
        {
            kPaint.setColor(Color.RED);
            player1=3;
            player=0;
        }
        else
        {kPaint.setColor(Color.YELLOW);
         player1=4;
         player=1;}

        int b=0;
        int j;
        while(x>(b*W))
        {
            b++;
        }
        b--;                // x co-ordinate of circle is the Bth slot i.e W+radius+radius*b (1,7)
        int f=0;
        for( j=5;j>=0;j--)
            if(!(a[b-1][j]==0))
            {   f=1;
                break;
            }
            j++;                    // j becomes the slot which is above the filled slot i.e one where it's going to drop (0,5)
            if(f==0)
            {mCanvas1.drawCircle(W+radius*(2*b-1),8*H+radius,radius,kPaint);
              a[b-1][0]=player1;}
            if(j>5);
            else
            {   if(!(f==0))
              {mCanvas1.drawCircle(W+radius*(2*b-1),(8-j)*H+radius,radius,kPaint);    //y co-ordinate 3*H+radius*((6-j)*2-1)
                a[b-1][j]=player1;}
            }
       move=count/2;
       c[move][player][0]=b-1;
       c[move][player][1]=j;

    }

    private void drawRect()
    {
        mCanvas2.drawColor(BackgroundColor);

        if(mBitmap2==null)
            {
            mBitmap2 = Bitmap.createBitmap((int)mWidth, (int) mHeigth, Bitmap.Config.ARGB_8888);
            mCanvas2 = new Canvas(mBitmap2);
             }

        mCanvas2.drawRect(mRect,mPaint1);
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 6; j++)
            {mCanvas2.drawCircle(radius + (i + 1) * W, (3 * H) + (j) * H+radius , radius, dPaint); }

        mCanvas2.drawRect(undorect,zPaint);
        mCanvas2.drawText("Undo",4*W,(W),txtPaint);

    }


}
