import React, { useState, useEffect } from "react";

export function EditTaskModal({ isOpen, onClose, onSave, task, errorMessage }) {
    const [taskTitle, setTaskTitle] = useState("");
    const [taskDescription, setTaskDescription] = useState("");
    const [taskStatus, setTaskStatus] = useState("");
    const [formError, setFormError] = useState("");

    const statusOptions = ["NEW", "IN_PROGRESS", "COMPLETED"];

    useEffect(() => {
        if (task) {
            setTaskTitle(task.title);
            setTaskDescription(task.description);
            setTaskStatus(task.status);
        }
    }, [task]);

    const handleSave = () => {
        if (!taskTitle.trim() || !taskDescription.trim() || !taskStatus.trim()) {
            setFormError("All fields are required.");
            return;
        }

        onSave(taskTitle, taskDescription, taskStatus);
    };

    if (!isOpen) return null;

    return (
        <div
            className="fixed inset-0 bg-gray-500 bg-opacity-50 z-50"
            onClick={onClose}
        >
            <div
                className="flex items-center justify-center h-full"
                onClick={(e) => e.stopPropagation()}
            >
                <div className="bg-white p-6 rounded-md shadow-lg w-1/3">
                    <h2 className="text-2xl font-bold mb-4">Edit Task</h2>
                    <input
                        type="text"
                        placeholder="Task Title"
                        value={taskTitle}
                        onChange={(e) => setTaskTitle(e.target.value)}
                        className="w-full p-2 mb-4 border border-gray-300 rounded"
                    />
                    <input
                        type="text"
                        placeholder="Task Description"
                        value={taskDescription}
                        onChange={(e) => setTaskDescription(e.target.value)}
                        className="w-full p-2 mb-4 border border-gray-300 rounded"
                    />
                    <select
                        value={taskStatus}
                        onChange={(e) => setTaskStatus(e.target.value)}
                        className="w-full p-2 mb-4 border border-gray-300 rounded"
                    >
                        <option value="">Select Status</option>
                        {statusOptions.map((status, index) => (
                            <option key={index} value={status}>
                                {status}
                            </option>
                        ))}
                    </select>
                    {formError && <p className="text-red-500 text-sm mb-2">{formError}</p>}
                    {errorMessage && <p className="text-red-500 text-sm mb-2">{errorMessage}</p>}
                    <div className="flex justify-between">
                        <button
                            onClick={onClose}
                            className="bg-gray-500 text-white p-2 rounded"
                        >
                            Cancel
                        </button>
                        <button
                            onClick={handleSave}
                            className="bg-blue-500 text-white p-2 rounded"
                        >
                            Save
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}
