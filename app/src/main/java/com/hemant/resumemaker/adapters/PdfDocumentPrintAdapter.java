package com.hemant.resumemaker.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PdfDocumentPrintAdapter extends PrintDocumentAdapter {

    Context context;
    String pathName;

    public PdfDocumentPrintAdapter(Context context, String pathName) {
        this.context = context;
        this.pathName = pathName;
    }

    @Override
    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes1, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {

        if(cancellationSignal.isCanceled()){
            layoutResultCallback.onLayoutCancelled();
        }
        else{
            PrintDocumentInfo.Builder builder = new PrintDocumentInfo.Builder(" file name");
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_UNKNOWN)
                    .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                    .build();
            layoutResultCallback.onLayoutFinished(builder.build(),
                    !printAttributes1.equals(printAttributes));
        }
    }

    @Override
    public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {

        InputStream in = null;
        OutputStream out = null;
        try{
            File file = new File(pathName);
            in = new FileInputStream(file);
            out = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());

            byte[] buffer = new byte[112000];
            int size;

            while((size = in.read(buffer)) >= 0 && !cancellationSignal.isCanceled()){
                out.write(buffer,0,size);
            }

            if(cancellationSignal.isCanceled()){
                writeResultCallback.onWriteCancelled();
            }
            else{
                writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
            }
        }
        catch (Exception e){
            writeResultCallback.onWriteFailed(e.getMessage());
        }
        finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
