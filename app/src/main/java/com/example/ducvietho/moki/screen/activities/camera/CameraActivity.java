package com.example.ducvietho.moki.screen.activities.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.screen.activities.postproduct.PostProductActivity;
import com.example.ducvietho.moki.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TuyenTiTon on 11/13/17.
 */

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, Camera.PictureCallback, SurfaceHolder
		.Callback {
	private static final String EXTRA_TIME = "time";
	private static final String KEY_IS_CAPTURING = "is_capturing";
	private static final int CAMERA_REQUEST = 10001;
	private static final int STORAGE_REQUEST = 10002;
	private static final int IMAGE_FROM_LIBRARY = 10003;
	private boolean mIsCapturing;
	private Camera mCamera;
	private int currentCameraId;

	private SurfaceHolder surfaceHolder;
	@BindView(R.id.surfaceView)
	SurfaceView mCameraPreview;
	@BindView(R.id.btnFlashOn)
	Button btFlashOn;
	@BindView(R.id.btnFlashOff)
	Button btFlashOff;
	@BindView(R.id.layoutImageView)
	RelativeLayout layoutViewImage;
	@BindView(R.id.imageCapture)
	ImageView imageView;
	private String pathFileOut;
	private int time;
	public static Intent getIntent(Context context,int time){
		Intent intent = new Intent(context,CameraActivity.class);
		intent.putExtra(EXTRA_TIME,time);
		return intent;
	}
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		ButterKnife.bind(CameraActivity.this);
		Intent intent = getIntent();
		time = intent.getIntExtra(EXTRA_TIME,0);
		btFlashOn.setVisibility(View.GONE);
		btFlashOn.setOnClickListener(this);
		btFlashOff.setOnClickListener(this);
		findViewById(R.id.btnSwitch).setOnClickListener(this);
		findViewById(R.id.imgPhoto).setOnClickListener(this);
		findViewById(R.id.btnCancel).setOnClickListener(this);
		findViewById(R.id.imgCapturePhoto).setOnClickListener(this);
		findViewById(R.id.icBack).setOnClickListener(this);
		findViewById(R.id.icDone).setOnClickListener(this);
		checkPermissionCamera();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
	}
	@Override
	public void onPictureTaken(byte[] bytes, Camera camera) {
		File outputFile = new File(getCacheDir(), "photo_" + SystemClock.currentThreadTimeMillis() + ".jpg");
		try {
			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(bytes);
			fos.close();
			Bitmap bitmap = resizeImage(800, outputFile.getPath());
			layoutViewImage.setVisibility(View.VISIBLE);
			imageView.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mCamera.stopPreview();
	}



	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
		if (mCamera != null) {
			try {
				mCamera.setDisplayOrientation(90);
				mCamera.setParameters(getCameraParams());
				mCamera.setPreviewDisplay(surfaceHolder);
				mCamera.startPreview();
			} catch (IOException e) {
				Toast.makeText(CameraActivity.this,"Unable to start camera preview.",Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

	}

	@Override
	public void onResume() {
		super.onResume();
		if (mCamera == null) {
			try {
				mCamera = Camera.open(currentCameraId);
				mCamera.setDisplayOrientation(90);
				mCamera.setParameters(getCameraParams());
				mCamera.setPreviewDisplay(surfaceHolder);
				mCamera.startPreview();
			} catch (Exception e) {
				Toast.makeText(CameraActivity.this,"Unable to open camera.",Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}



	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean(KEY_IS_CAPTURING, mIsCapturing);
	}



	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CAMERA_REQUEST) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				initCapture();
			} else {
				finish();
			}
		} else if (requestCode == STORAGE_REQUEST) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				initPickLibrary();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == IMAGE_FROM_LIBRARY) {
			ArrayList<Image> images =
					data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
			Bitmap bitmap = resizeImage(800, images.get(0).path);

			layoutViewImage.setVisibility(View.VISIBLE);
			imageView.setImageBitmap(bitmap);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btnCancel:
				finish();
				overridePendingTransition(R.anim.no_change, R.anim.slide_down_info);
				break;
			case R.id.btnFlashOn:
				flashLightOff();
				break;
			case R.id.btnFlashOff:
				flashLightOn();
				break;
			case R.id.btnSwitch:
				switchCamera();
				break;
			case R.id.imgCapturePhoto:
				mCamera.takePicture(null, null, this);
				break;
			case R.id.imgPhoto:
				checkPermissionLibrary();
				break;
			case R.id.icBack:
				try {
					mCamera.setDisplayOrientation(90);
					mCamera.startPreview();

					layoutViewImage.setVisibility(View.GONE);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.icDone:
				if(time>1){
					Intent intent = new Intent();
					intent.putExtra("path", pathFileOut);
					setResult(Activity.RESULT_OK,intent);
					finish();
				}
				else {
					startActivity(new PostProductActivity().getIntent(CameraActivity.this,pathFileOut));
					finish();
				}
				break;
			default:
				break;
		}
	}
	private void checkPermissionCamera() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
		} else {
			initCapture();
		}
	}

	private void checkPermissionLibrary() {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_REQUEST);
		} else {
			initPickLibrary();
		}
	}

	private void initCapture() {
		currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
		surfaceHolder = mCameraPreview.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mIsCapturing = true;
	}
	private Bitmap resizeImage(int maxSize, String pathFile) {
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathFile, bmOptions);
		int srcWidth = bmOptions.outWidth;
		int srcHeight = bmOptions.outHeight;
		if (srcWidth > srcHeight) {
			bmOptions.inDensity = srcWidth;
		} else {
			bmOptions.inDensity = srcHeight;
		}
		bmOptions.inScaled = true;
		bmOptions.inTargetDensity = maxSize;

		bmOptions.inJustDecodeBounds = false;
		pathFileOut = pathFile;

		if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			Matrix matrix = new Matrix();
			matrix.setRotate(180);
			Bitmap b = BitmapFactory.decodeFile(pathFile, bmOptions);
			return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
		}

		return BitmapFactory.decodeFile(pathFile, bmOptions);
	}
	private Camera.Parameters getCameraParams() {
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.set("orientation", "portrait");
		parameters.setRotation(90);
		List<Camera.Size> mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
		Camera.Size mPreviewSize;
		if (mSupportedPreviewSizes != null) {
			mPreviewSize = getOptimalPreviewSize(
					mSupportedPreviewSizes,
					Utils.getScreenWidth(this),
					Utils.getScreenHeigh(this)
			);
			int h = mPreviewSize.height;
			int w = mPreviewSize.width;
			parameters.setPreviewSize(w, h);
			parameters.setPictureSize(w, h);
		}
		if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		}
		parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
		return parameters;
	}

	private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) h / w;
		if (sizes == null) return null;
		Camera.Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;
		int targetHeight = h;
		for (Camera.Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Camera.Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	private void flashLightOn() {
		try {
			mCamera.startPreview();
			Camera.Parameters p = getCameraParams();
			p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			mCamera.setParameters(p);
			mCamera.setDisplayOrientation(90);
			mCamera.startPreview();

			btFlashOn.setVisibility(View.VISIBLE);
			btFlashOff.setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(CameraActivity.this,"Unable to on flash light",Toast.LENGTH_LONG).show();
		}
	}

	private void flashLightOff() {
		try {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = Camera.open();
			mCamera.setParameters(getCameraParams());
			mCamera.setPreviewDisplay(mCameraPreview.getHolder());
			mCamera.setDisplayOrientation(90);
			mCamera.startPreview();

			btFlashOff.setVisibility(View.VISIBLE);
			btFlashOn.setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(CameraActivity.this,"Unable to off flash light",Toast.LENGTH_LONG);
		}
	}

	private void switchCamera() {
		if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
			currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
		} else if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
		}
		try {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = Camera.open(currentCameraId);
			mCamera.setParameters(getCameraParams());
			mCamera.setPreviewDisplay(mCameraPreview.getHolder());
			mCamera.setDisplayOrientation(90);
			mCamera.startPreview();
		} catch (Exception e) {
			Toast.makeText(CameraActivity.this,"Unable to open camera",Toast.LENGTH_LONG).show();
		}
	}
	private void initPickLibrary() {
		Intent intent = new Intent(this, AlbumSelectActivity.class);
		intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
		startActivityForResult(intent, IMAGE_FROM_LIBRARY);
		mCamera.stopPreview();
	}
}
