import React, { useState } from "react";

export function AddTaskModal({ isOpen, onClose, onSave, categoryId, errorMessage }) {
    const [taskTitle, settaskTitle] = useState("");
    const [taskDescription, setTaskDescription] = useState("");
    const [taskStatus, setTaskStatus] = useState("");
    const [statusOptions] = useState(["NEW", "IN_PROGRESS", "COMPLETED"]);
    const [formTitleError, setFormTitleError] = useState(null);
    const [formDescriptionError, setFormDescriptionError] = useState(null);
    const [formStatusError, setFormStatusError] = useState(null);

    const handleSaveTask = () => {
        resetFormErrors();
        let error = false;
        if (!taskTitle.trim()) {
            setFormTitleError("Task title is required.");
            error = true;
        }

        if (!taskDescription.trim()) {
            setFormDescriptionError("Task description is required.");
            error = true;
        }

        if (!taskStatus.trim()) {
            setFormStatusError("Task status is required.");
            error = true;
        }

        if(error) return;
        
        onSave(taskTitle, taskDescription, taskStatus, categoryId);
        settaskTitle("");
        setTaskDescription("");
        setTaskStatus("");
    };

    if (!isOpen) return null;

    const resetFormErrors = () => {
        setFormTitleError(null)
        setFormDescriptionError(null);
        setFormStatusError(null);
    }

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
                    <h2 className="text-2xl font-bold mb-4">Add New Task</h2>

                    <input
                        type="text"
                        placeholder="Task Title"
                        value={taskTitle}
                        onChange={(e) => settaskTitle(e.target.value)}
                        className="w-full p-2 mb-4 border border-gray-300 rounded"
                    />
                    {formTitleError && (
                        <p className="text-red-500 text-sm mb-2">{formTitleError}</p>
                    )}

                    <textarea
                        placeholder="Task Description"
                        value={taskDescription}
                        onChange={(e) => setTaskDescription(e.target.value)}
                        className="w-full p-2 mb-4 border border-gray-300 rounded"
                    />
                    {formDescriptionError && (
                        <p className="text-red-500 text-sm mb-2">{formDescriptionError}</p>
                    )}

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
                    {formStatusError && (
                        <p className="text-red-500 text-sm mb-2">{formStatusError}</p>
                    )}

                    {errorMessage && (
                        <p className="text-red-500 text-sm mb-2">{errorMessage}</p>
                    )}

                    <div className="flex justify-between">
                        <button
                            onClick={() => {
                                onClose();
                                resetFormErrors();
                            }}
                            className="bg-gray-500 text-white p-2 rounded"
                        >
                            Cancel
                        </button>
                        <button
                            onClick={handleSaveTask}
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
