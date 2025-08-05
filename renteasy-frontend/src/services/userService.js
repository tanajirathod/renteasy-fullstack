// src/services/userService.js
import apiClient from './apiClient';

const BASE_PATH = '/users';

const registerUser = (userData) => apiClient.post(`${BASE_PATH}/register`, userData);
const loginUser = (credentials) => apiClient.post(`${BASE_PATH}/login`, credentials);
const getCurrentUser = () => apiClient.get(`${BASE_PATH}/me`);
const logoutUser = () => apiClient.post(`${BASE_PATH}/logout`);

const userService = {
  registerUser,
  loginUser,
  getCurrentUser,
  logoutUser,
};

export default userService;
