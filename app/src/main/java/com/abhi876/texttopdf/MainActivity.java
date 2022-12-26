package com.abhi876.texttopdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button convert_button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        convert_button = findViewById(R.id.convert_button);
        editText = findViewById(R.id.editText);

        convert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if (str != null && !str.trim().isEmpty()) {
                    convertToPdf(str);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter some text in the text box below to convert..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void convertToPdf(String text) {
        //   String text = "Lorem ipsum...very long text";
        ArrayList<String> texts = new ArrayList<>();
        int tot_char_count = 0;
        //Counts total characters in the long text
        for (int i = 0; i < text.length(); i++) {
            tot_char_count++;
        }
        int per_page_words = 4900;
        int pages = tot_char_count / per_page_words;
        int remainder_pages_extra = tot_char_count % per_page_words;
        if (remainder_pages_extra > 0) {
            pages++;
        }
        int k = pages, count = 0;
        while (k != 0) {
            StringBuilder each_page_text = new StringBuilder();
            for (int y = 0; y < per_page_words; y++) {
                if (count < tot_char_count) {
                    each_page_text.append(text.charAt(count));
                    if (y == (per_page_words - 1) && text.charAt(count) != ' ') {
                        while (text.charAt(count) != '\n') {
                            count++;
                            each_page_text.append(text.charAt(count));
                        }
                    } else {
                        count++;
                    }
                }
            }
            texts.add(each_page_text.toString());
            k--;
        }

        PdfDocument pdfDocument = new PdfDocument();
        int pageNumber = 0;
        try {
            pageNumber++;
            for (String each_page_text : texts) {
                PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(595, 842, pageNumber).create();
                PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
                Canvas canvas = myPage.getCanvas();
                TextPaint mTextPaint = new TextPaint();
                mTextPaint.setTextSize(11);
                //  mTextPaint.setTypeface(ResourcesCompat.getFont(this, R.font.roboto));
                StaticLayout mTextLayout = new StaticLayout(each_page_text, mTextPaint, canvas.getWidth() - 60, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                canvas.save();
                int textX = 30;
                int textY = 30;
                canvas.translate(textX, textY);
                mTextLayout.draw(canvas);
                canvas.restore();
                pdfDocument.finishPage(myPage);
            }
            File file = new File(getFilesDir(), "GeneratedFile.pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            pdfDocument.writeTo(fOut);
            //  Toast.makeText(context, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }
}