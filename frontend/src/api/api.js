import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true,  // Umożliwia obsługę ciasteczek httpOnly
    headers: {
        'Content-Type': 'application/json',
    },
});

// Task API
export const editTask = (id, taskData) => {
    return api.post(`/task/edit/${id}`, taskData);
};

export const createTask = (taskData) => {
    return api.post('/task/create', taskData);
};

export const changeTaskCategory = (taskId, newCategoryId) => {
    return api.post(`/task/change-category/${taskId}/${newCategoryId}`);
};

export const getTaskById = (id) => {
    return api.get(`/task/get/${id}`);
};

export const deleteTask = (id) => {
    return api.delete(`/task/delete/${id}`);
};

export const listTasks = () => {
    return api.get('/task/list');
};

// Category API
export const editCategory = (id, categoryData) => {
    return api.post(`/category/edit/${id}`, categoryData);
};

export const createCategory = (categoryData) => {
    return api.post('/category/create', categoryData);
};

export const getCategoryById = (id) => {
    return api.get(`/category/get/${id}`);
};

export const deleteCategory = (id) => {
    return api.delete(`/category/delete/${id}`);
};

export const listCategories = () => {
    return api.get('/category/list');
};

// Auth API
export const register = (userData) => {
    return api.post('/auth/register', userData);
};

export const login = (loginData) => {
    return api.post('/auth/login', loginData);
};

export const logout = () => {
    return api.get('/auth/logout');
};

const getCookie = (name) => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
};

export const isUserLoggedIn = () => {
    const token = getCookie('jwt');
    if (token) {
        return true;
    }
    return false;
};
