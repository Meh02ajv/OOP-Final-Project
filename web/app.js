document.addEventListener('DOMContentLoaded', () => {
    const dropZone = document.getElementById('drop-zone');
    const fileInput = document.getElementById('file-input');
    const previewContainer = document.getElementById('preview-container');
    const imagePreview = document.getElementById('image-preview');
    const removeBtn = document.getElementById('remove-image');
    const analyzeBtn = document.getElementById('analyze-btn');
    const loader = document.querySelector('.loader');
    const btnText = document.querySelector('.btn-text');
    const resultsSection = document.getElementById('results-section');

    let selectedFile = null;

    // Drag and Drop Logic
    dropZone.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropZone.classList.add('dragover');
    });

    dropZone.addEventListener('dragleave', () => {
        dropZone.classList.remove('dragover');
    });

    dropZone.addEventListener('drop', (e) => {
        e.preventDefault();
        dropZone.classList.remove('dragover');
        if (e.dataTransfer.files.length) {
            handleFile(e.dataTransfer.files[0]);
        }
    });

    // File Input Logic
    fileInput.addEventListener('change', (e) => {
        if (e.target.files.length) {
            handleFile(e.target.files[0]);
        }
    });

    function handleFile(file) {
        if (!file.type.startsWith('image/')) {
            alert('Please upload an image file');
            return;
        }

        selectedFile = file;
        const reader = new FileReader();
        reader.onload = (e) => {
            imagePreview.src = e.target.result;
            previewContainer.classList.remove('hidden');
            analyzeBtn.classList.remove('hidden');
            resultsSection.classList.add('hidden');
        };
        reader.readAsDataURL(file);
    }

    // Remove Image Logic
    removeBtn.addEventListener('click', (e) => {
        e.stopPropagation(); // Prevent clicking dropzone
        selectedFile = null;
        fileInput.value = '';
        previewContainer.classList.add('hidden');
        analyzeBtn.classList.add('hidden');
        resultsSection.classList.add('hidden');
    });

    // Analyze Button Logic
    analyzeBtn.addEventListener('click', async () => {
        if (!selectedFile) return;

        setLoading(true);

        try {
            // Send the actual file binary data!
            const response = await fetch('/analyze-image', {
                method: 'POST',
                headers: {
                    'Content-Type': selectedFile.type
                },
                body: selectedFile // Browsers automatically stream the file content here
            });
            
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || 'Analysis failed');
            }

            const data = await response.json();
            displayResults(data);

        } catch (error) {
            console.error(error);
            alert('Error analyzing image: ' + error.message);
        } finally {
            setLoading(false);
        }
    });

    function setLoading(isLoading) {
        analyzeBtn.disabled = isLoading;
        if (isLoading) {
            loader.classList.remove('hidden');
            btnText.classList.add('hidden');
        } else {
            loader.classList.add('hidden');
            btnText.classList.remove('hidden');
        }
    }

    function displayResults(data) {
        document.getElementById('meal-name').textContent = data.mealName;
        
        // Update totals
        document.getElementById('carbon-value').textContent = data.totals.carbonFootprint;
        document.getElementById('water-value').textContent = data.totals.waterUsage;
        document.getElementById('land-value').textContent = data.totals.landUsage;
        document.getElementById('nitrogen-value').textContent = data.totals.nitrogenWaste;

        // Populate ingredients
        const container = document.getElementById('ingredients-container');
        container.innerHTML = '';
        
        data.items.forEach(item => {
            const li = document.createElement('li');
            li.className = 'ingredient-item';
            li.innerHTML = `
                <span class="ingredient-name">${item.name}</span>
                <span class="ingredient-weight">${item.portionKg} kg</span>
            `;
            container.appendChild(li);
        });

        const carKm = (data.totals.carbonFootprint * 4).toFixed(1);
        document.getElementById('car-km').textContent = carKm;
        const buckets = (data.totals.waterUsage / 18).toFixed(1);
        document.getElementById('water-buckets').textContent = buckets;

        const pitches = (data.totals.landUsage / 7140).toFixed(2);
        document.getElementById('football-pitch').textContent = pitches;
        resultsSection.classList.remove('hidden');
        lucide.createIcons();
        resultsSection.scrollIntoView({ behavior: 'smooth' });
    }
});
