import React, { useState, useEffect } from "react";

export function EditCategoryModal({ isOpen, onClose, onSave, category, errorMessage }) {
    const [categoryName, setCategoryName] = useState("");
    const [formError, setFormError] = useState(null);

    useEffect(() => {
        if (category) {
            setCategoryName(category.name);
        }
    }, [category]);

    const handleSaveCategory = () => {
        setFormError(null);

        if (!categoryName.trim()) {
            setFormError("Category name is required.");
            return;
        }

        onSave(categoryName);
        setCategoryName("");
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
                    <h2 className="text-2xl font-bold mb-4">Edit Category</h2>

                    <input
                        type="text"
                        placeholder="Category Name"
                        value={categoryName}
                        onChange={(e) => setCategoryName(e.target.value)}
                        className="w-full p-2 mb-4 border border-gray-300 rounded"
                    />
                    {formError && (
                        <p className="text-red-500 text-sm mb-2">{formError}</p>
                    )}

                    {errorMessage && (
                        <p className="text-red-500 text-sm mb-2">{errorMessage}</p>
                    )}

                    <div className="flex justify-between">
                        <button
                            onClick={() => {
                                onClose();
                                setFormError(null);
                            }}
                            className="bg-gray-500 text-white p-2 rounded"
                        >
                            Cancel
                        </button>
                        <button
                            onClick={handleSaveCategory}
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
