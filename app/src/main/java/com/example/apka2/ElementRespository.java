package com.example.apka2;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ElementRespository {
    private ElementDao mElementDao;
    private LiveData<List<Element>> mAllElements;

    ElementRespository(Application application) {
        ElementRoomDatabase elementRoomDatabase = ElementRoomDatabase.getDatabase(application);
        mElementDao = elementRoomDatabase.elementDao();
        mAllElements = mElementDao.getAlphabetizedElements();
    }

    LiveData<List<Element>> getAllElements() {
        return mAllElements;
    }

    void deleteAll() {
        new DeleteAllNoteAsyncTask(mElementDao).execute();
    }

    public void insert(Element element) {
        new InsertNoteAsyncTask(mElementDao).execute(element);
    }

    LiveData<List<Element>> getAlphabetizedElements() {
        return mAllElements;
    }
    public void update(Element element){
        new UpdateNoteAsyncTask(mElementDao).execute(element);
    }

    public void delete(Element element){
        new DeleteNoteAsyncTask(mElementDao).execute(element);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Element, Void, Void> {
        private ElementDao elementDao;

        private InsertNoteAsyncTask(ElementDao elementDao) {
            this.elementDao = elementDao;
        }

        @Override
        protected Void doInBackground(Element... elements) {
            elementDao.insert(elements[0]);
            return null;
        }
    }


    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private ElementDao elementDao;

        private DeleteAllNoteAsyncTask(ElementDao elementDao) {
            this.elementDao = elementDao;
        }

        @Override
        protected Void doInBackground(Void ... voids) {
            elementDao.deleteAll();
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Element, Void, Void> {
        private ElementDao elementDao;

        private UpdateNoteAsyncTask(ElementDao elementDao) {
            this.elementDao = elementDao;
        }

        @Override
        protected Void doInBackground(Element... elements) {
            elementDao.update(elements[0]);
            return null;
        }
    }


    private static class DeleteNoteAsyncTask extends AsyncTask<Element, Void, Void> {
        private ElementDao elementDao;

        private DeleteNoteAsyncTask(ElementDao elementDao) {
            this.elementDao = elementDao;
        }

        @Override
        protected Void doInBackground(Element... elements) {
            elementDao.delete(elements[0]);
            return null;
        }
    }
}
