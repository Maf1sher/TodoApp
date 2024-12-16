import React, { useEffect, useState } from "react";
import {
    listCategories,
    isUserLoggedIn,
    createCategory,
    deleteCategory,
    createTask,
    deleteTask,
    editTask,
    editCategory
} from "../api/api.js";
import { NavBar } from "../components/NavBar.jsx";
import { useNavigate } from "react-router-dom";
import { AddCategoryModal } from "../components/AddCategoryModal";
import { AddTaskModal } from "../components/AddTaskModal";
import trashIcon from "../assets/trashIcon.png";
import {EditTaskModal} from "../components/EditTaskModal.jsx";
import {EditCategoryModal} from "../components/EditCategoryModal.jsx";

export function Home() {
    const [categories, setCategories] = useState([]);
    const [error, setError] = useState(null);
    const [isCategoryModalOpen, setIsCategoryModalOpen] = useState(false);
    const [isTaskModalOpen, setIsTaskModalOpen] = useState(false);
    const [isEditTaskModalOpen, setIsEditTaskModalOpen] = useState(false);
    const [selectedCategoryId, setSelectedCategoryId] = useState(null);
    const [selectedTask, setSelectedTask] = useState(null);
    const [taskError, setTaskError] = useState("");
    const [isEditCategoryModalOpen, setIsEditCategoryModalOpen] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        const loggedIn = isUserLoggedIn();

        if (loggedIn) {
            listCategories()
                .then((response) => {
                    setCategories(response.data);
                })
        } else {
            navigate("/login");
        }
    }, [categories]);

    const handleAddCategory = () => {
        setIsCategoryModalOpen(true);
    };

    const handleCloseCategoryModal = () => {
        setIsCategoryModalOpen(false);
    };

    const handleSaveCategory = (newCategoryName) => {
        createCategory(newCategoryName)
            .then(() => {
                setIsCategoryModalOpen(false);
            })
            .catch(() => {
                setTaskError("Category already exists");
            });
    };

    const handleAddTask = (categoryId) => {
        setSelectedCategoryId(categoryId);
        setIsTaskModalOpen(true);
    };

    const handleCloseTaskModal = () => {
        setIsTaskModalOpen(false);
        setIsEditTaskModalOpen(false);
        setTaskError("");
        setSelectedTask(null);
    };

    const handleSaveTask = (taskTitle, taskDescription, taskStatus) => {
        if (!taskTitle.trim() || !taskDescription.trim() || !taskStatus.trim()) {
            setTaskError("All fields are required.");
            return;
        }

        createTask({
            title: taskTitle,
            description: taskDescription,
            status: taskStatus,
            category_id: selectedCategoryId,
        }).then(() => {
            handleCloseTaskModal();
        }).catch(() => {
            setTaskError("Failed to create task");
        })

    };

    const handleSaveEditedTask = (taskTitle, taskDescription, taskStatus) => {
        if (!taskTitle.trim() || !taskDescription.trim() || !taskStatus.trim()) {
            setTaskError("All fields are required.");
            return;
        }

        editTask(selectedTask.id,{
            title: taskTitle,
            description: taskDescription,
            status: taskStatus
        }).then(() => {
            handleCloseTaskModal();
        }).catch(() => {
            setTaskError("Failed to edit task.");
        });
    };

    const handleDeleteCategory = (categoryId) => {
        deleteCategory(categoryId)
            .catch(() => {
                setError("Failed to delete category.");
            });
    };

    const handleDeleteTask = (taskId) => {
        deleteTask(taskId)
            .catch(() => {
                setError("Failed to delete task.");
            });
    };

    const handleEditTask = (task) => {
        setSelectedTask(task);
        setIsEditTaskModalOpen(true);
    }

    const handleEditCategory = (category) => {
        setSelectedCategory(category);
        setIsEditCategoryModalOpen(true);
    };

    const handleSaveEditedCategory = (categoryName) => {

        editCategory(selectedCategory.id, { name: categoryName })
            .then(() => {
                setIsEditCategoryModalOpen(false);
                setSelectedCategory(null);
            })
            .catch(() => {
                setTaskError("Failed to edit category.");
            });
    };

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <>
            <NavBar />
            <div className="flex min-h-full flex-col justify-center px-6 py-12 md:px-16 lg:px-16 xl:px-32">
                <div className="flex flex-col w-full items-center">
                    <button
                        className="ring-1 w-1/4 ring-black rounded flex items-center p-1"
                        onClick={handleAddCategory}
                    >
                        <p className="text-3xl">+</p>
                        <p className="text-2xl">Category</p>
                    </button>
                    {categories.length === 0 ? (
                        <p>No categories available.</p>
                    ) : (
                        <ul className="px-6 lg:px-64 w-full">
                            {categories.map((category) => (
                                <li key={category.id} className="ring-1 ring-black rounded m-4">
                                    <h2 className="ring-1 ring-black m-0 rounded p-5 flex justify-center text-2xl font-bold">
                                        <p className="w-full flex justify-center">{category.name}</p>
                                        <button
                                            className="ml-2 text-green-500 text-5xl px-1"
                                            onClick={() => handleAddTask(category.id)}
                                        >
                                            +
                                        </button>
                                        <button className="w-[50px] px-1"
                                                onClick={() => handleDeleteCategory(category.id)}>
                                            <img src={trashIcon} alt="trash icon"/>
                                        </button>
                                        <button className="ml-2 text-blue-500 text-xl px-1"
                                                onClick={() => handleEditCategory(category)}>
                                            Edit
                                        </button>
                                    </h2>
                                    {category.tasks && category.tasks.length > 0 ? (
                                        <ul className="p-5">
                                            {category.tasks.map((task) => (
                                                <li key={task.id}
                                                    className="ring-1 ring-black rounded p-2 m-2 flex justify-between">
                                                    <div className="w-9/12">
                                                        <div className="text-xl font-bold">
                                                            <p>{task.title}</p>
                                                        </div>
                                                        <div className="my-2 pr-1 text-balance break-words">
                                                            {task.description}
                                                        </div>
                                                        <div className="text-red-500">
                                                            <p>{task.status}</p>
                                                        </div>

                                                    </div>
                                                    <div className="flex w-auto">
                                                        <button className="w-[30px]"
                                                                onClick={() => handleDeleteTask(task.id)}
                                                        >
                                                            <img src={trashIcon} alt="trash icon"/>
                                                        </button>
                                                        <button
                                                            className="text-blue-500 p-2"
                                                            onClick={() => handleEditTask(task)}
                                                        >
                                                            Edit
                                                        </button>
                                                    </div>
                                                </li>
                                            ))}
                                        </ul>
                                    ) : (
                                        <div className="w-full flex justify-center">
                                            <p>No tasks available for this category.</p>
                                        </div>

                                    )}
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>

            <AddCategoryModal
                isOpen={isCategoryModalOpen}
                onClose={handleCloseCategoryModal}
                onSave={handleSaveCategory}
                errorMessage={taskError}
            />

            <AddTaskModal
                isOpen={isTaskModalOpen}
                onClose={handleCloseTaskModal}
                onSave={handleSaveTask}
                errorMessage={taskError}
                categoryId={selectedCategoryId}
            />

            <EditTaskModal
                isOpen={isEditTaskModalOpen}
                onClose={handleCloseTaskModal}
                onSave={handleSaveEditedTask}
                errorMessage={taskError}
                categoryId={selectedCategoryId}
                task={selectedTask}
            />

            <EditCategoryModal
                isOpen={isEditCategoryModalOpen}
                onClose={() => setIsEditCategoryModalOpen(false)}
                onSave={handleSaveEditedCategory}
                errorMessage={taskError}
                category={selectedCategory}
            />
        </>
    );
}
