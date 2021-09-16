package com.example.filteractivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.filteractivity.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float FACE_POSITION_RADIUS = 20.0f;
    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float GENERIC_POS_OFFSET = 20.0f;
    private static final float GENERIC_NEG_OFFSET = -20.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private static final int MASK[] = {

            R.drawable.snap,

            R.drawable.dog,
            R.drawable.cat2
    };



    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;
    private Bitmap bitmap;
    private Bitmap op;

    FaceGraphic(GraphicOverlay overlay,int c) {
        super(overlay);

        bitmap = BitmapFactory.decodeResource(getOverlay().getContext().getResources(),MASK[c]);
        op = bitmap;
    }

    void setId(int id) {
        mFaceId = id;
    }

    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face,int c) {
        mFace = face;
        bitmap = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), MASK[c]);
        op = bitmap;
        op = Bitmap.createScaledBitmap(op, (int) scaleX(face.getWidth()),
                (int) scaleY(((bitmap.getHeight() * face.getWidth()) / bitmap.getWidth())), false);
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if(face == null) return;
        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2.6f);
        float y = translateY(face.getPosition().y + face.getHeight() / 2.6f);

        float xOffset = scaleX(face.getWidth() / 1.7f);
        float yOffset = scaleY(face.getHeight() / 1.7f);
        float left = x - xOffset;
        float top = y - yOffset;

        canvas.drawBitmap(op, left, top, new Paint());
    }

}
