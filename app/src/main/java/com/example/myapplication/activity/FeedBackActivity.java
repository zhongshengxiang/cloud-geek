package com.example.myapplication.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.utils.Toaster;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.subscriptions.CompositeSubscription;


public class FeedBackActivity extends BaseActivity {
    ArrayList<String> imgPath = new ArrayList<>();
    List<String> imaNetPath = new ArrayList<>();
    CompositeSubscription uploadCompositeSubscription = new CompositeSubscription();
    SweetAlertDialog dialog;
    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    @BindView(R.id.et_content) EditText mEtContent;
    @BindView(R.id.hint) TextView mHint;
    @BindView(R.id.add) ImageView mAdd;
    @BindView(R.id.container) GridLayout mContainer;


    @Override
    public int getLayoutID() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initView() {
        initEdittext();
//        setTitle("使用帮助与反馈", "发送", new TitleOnClickListener() {
//            @Override
//            public void rightOnClick(View v) {
//                String content = mEtContent.getText().toString();
//                if (TextUtils.isEmpty(content)) {
//                    ToastUtil.show("请先填写描述或建议...");
//                } else if (imgPath.size() == 0) {
//                    ToastUtil.show("清添加图片说明");
//                } else {
//                    uploadima(0, content);
//                }
//            }
//        });
        initAdd();
    }

    private void initAdd() {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }


    private void uploadima(final int positon, final String content) {
        //先上传照片
        final File file = new File(imgPath.get(positon));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);

//        final Subscription sub =
//                mWebApi.uploadFile(requestBody)
//                        .compose(RxTransformerHelper.<ApiResult<UploadFileResult>>applySchedulers())
//                        .doOnSubscribe(new Action0() {
//                            @Override
//                            public void call() {
//                                if (dialog == null || !dialog.isShowing()) {
//                                    dialog = DialogUtil.getIntance().getProgressDialog(HelpAndFeedbackActivity.this,
//                                            "正在上传第" + (positon + 1) + "张图片...", null);
//                                    dialog.setCancelable(true);
//                                    dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                        @Override
//                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                            dialog.dismiss();
//                                            Toaster.show("您已取消");
//                                            imaNetPath.clear();
//                                            uploadCompositeSubscription.clear();
//                                        }
//                                    });
//                                    dialog.show();
//                                } else {
//                                    dialog.setTitleText("正在上传第" + (positon + 1) + "张图片...");
//                                }
//                            }
//                        })
//                        .subscribeOn(AndroidSchedulers.mainThread())
//                        .compose(RxTransformerHelper.<UploadFileResult>checkResponse())
//                        .retry(1)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new MyExceptionSubscriber<UploadFileResult>() {
//                            @Override
//                            public void onCompleted() {
//                            }
//
//                            @Override
//                            public void onNext(UploadFileResult result) {
//                                imaNetPath.add(result.fId);
//                                if (imgPath.size() - 1 == positon) {
//                                    //上传图文信息
//                                    commit(content);
//                                } else {
//                                    //继续上传照片
//                                    uploadima(positon + 1, content);
//                                }
//                            }
//                        });
//        uploadCompositeSubscription.add(sub);
    }

    private void commit(String content) {
        StringBuilder files = new StringBuilder();
        for (int i = 0; i < imaNetPath.size(); i++) {
            files.append(imaNetPath.get(i));
            if (i != imaNetPath.size() - 1) {
                files.append(",");
            }
        }
//        Subscription subscribe = service.feedBack(GlobalConfig.getTeacherID(), content, files.toString())
//                .compose(RxTransformerHelper.<ApiResult<EmptyResult>>applySchedulers())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        if (dialog == null || !dialog.isShowing()) {
//                            dialog = DialogUtil.getIntance().getProgressDialog(HelpAndFeedbackActivity.this,
//                                    "正在提交反馈...", null);
//                            dialog.setCancelable(true);
//                            dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    dialog.dismiss();
//                                    Toaster.show("您已取消");
//                                }
//                            });
//                            dialog.show();
//                        } else {
//                            dialog.setTitleText("正在提交反馈......");
//                        }
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .compose(RxTransformerHelper.<EmptyResult>checkResponse())
//                .subscribe(new MyExceptionSubscriber<EmptyResult>() {
//                    @Override
//                    public void onNext(EmptyResult emptyResult) {
//                        Toaster.show("提交成功");
//                        finish();
//                    }
//                });
//        addSubscription(subscribe);
    }

    private void showDialog() {
        new AlertDialog.Builder(this).setItems(
                new String[]{"拍摄照片", "从相册选择"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhoto();
                                break;
                            case 1:
                                pickPhoto();
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    Uri photoUri;

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        //执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            /**-----------------*/
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            doPhoto(requestCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Toaster.show("没有选择图片");
        }

    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) { //从相册取图片，有些手机有异常情况，请注意
            if (data == null) {
                Toaster.show("选择图片文件出错");
                return;
            }
            photoUri = data.getData();
            if (photoUri == null) {
                Toaster.show("选择图片文件出错");
                return;
            }
        }
        String picPath = null;
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            if (Build.VERSION.SDK_INT < 14) {
                cursor.close();
            }
        }

        if (picPath != null && (picPath.endsWith(".png") || picPath.endsWith(".PNG") || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
            final View inflate = getLayoutInflater().inflate(R.layout.add_img, null);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.img);
            Glide.with(this).load(picPath).into(imageView);
            final String finalPicPath = picPath;
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.img:
//                            ZoomImageActivity.showActivity(HelpAndFeedbackActivity.this, imgPath, imgPath.indexOf(finalPicPath));
                            break;
                        case R.id.delete:
                            imgPath.remove(finalPicPath);
                            mContainer.removeView(inflate);
                            break;
                        default:
                            break;
                    }
                }
            };
            inflate.findViewById(R.id.delete).setOnClickListener(listener);
            imageView.setOnClickListener(listener);
            imgPath.add(finalPicPath);

            mContainer.addView(inflate, mContainer.getChildCount() - 1);
        } else {
            Toaster.show("选择图片文件不正确");
        }

    }

    private void initEdittext() {
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mHint.setText("还可输入" + (500 - s.length()) + "字");
            }
        });
    }

    public static void start(Activity context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uploadCompositeSubscription.isUnsubscribed())
            uploadCompositeSubscription.unsubscribe();
    }
}
