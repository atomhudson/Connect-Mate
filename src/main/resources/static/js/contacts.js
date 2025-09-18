console.log("Con..............!");
const baseURL = "http://localhost:8081";
// const baseURL = "https://www.scm20.site";
const viewContactModal = document.getElementById("view_contact_modal");
const viewQueryModal = document.getElementById("view_query_modal");

const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onShow: () => {
    setTimeout(() => {
      if (contactModal) {
        contactModal.classList.add("scale-100");
      }else{
        queryModal.classList.add("scale-100");
      }
    }, 50);
  },
};

// instance options object
const instanceOptions = {
  id: "view_contact_modal",
  override: true,
};
const instanceQueryOptions = {
  id: "view_query_modal",
  override: true,
};

// Assuming Modal is a predefined class (or a UI library class)
const contactModal = new Modal(viewContactModal, options, instanceOptions);
const queryModal = new Modal(viewQueryModal, options, instanceQueryOptions);

function openContactModal() {
  contactModal.show();
}

function closeContactModal() {
  contactModal.hide();
}

function openQueryModal() {
  queryModal.show();
}

function closeQueryModel() {
  queryModal.hide();
}

async function loadContactdata(id) {
  //function call to load data
  try {
    const data = await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
    document.querySelector("#contact_name").innerHTML = data.name;
    document.querySelector("#contact_email").innerHTML = data.email;
    document.querySelector("#contact_image").src = data.picture;
    document.querySelector("#contact_address").innerHTML = data.address;
    document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
    document.querySelector("#contact_about").innerHTML = data.description;
    const contactFavorite = document.querySelector("#contact_favorite");
    if (data.favorite) {
      contactFavorite.innerHTML =
          "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
    } else {
      contactFavorite.innerHTML = "Not Favorite Contact";
    }

    let websiteLink = data.websiteLink;
    if (websiteLink && !websiteLink.startsWith('http')) {
      websiteLink = 'https://' + websiteLink;
    }
    document.querySelector("#contact_website").href = websiteLink;
    document.querySelector("#contact_website").innerHTML = data.websiteLink;
    let linkedInLink = data.linkedInLink;
    if (linkedInLink && !linkedInLink.startsWith('http')) {
      linkedInLink = 'https://' + linkedInLink;
    }
    document.querySelector("#contact_linkedIn").href = linkedInLink;

    document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink;
    openContactModal();
  } catch (error) {
    console.warn("Error: ", error);
  }
}

// delete contact
async function deleteContact(id) {
  Swal.fire({
    title: "Do you want to delete the contact?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Delete",
    customClass: {
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/user/contacts/delete/` + id;
      window.location.replace(url);
    }
  });
}

async function isEnabled(email, status) {
  const action = status ? "disable" : "enable";
  const title = `Do you want to ${action} this user?`;

  Swal.fire({
    title: title,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirm",
    customClass: {
      actions: 'flex justify-center space-x-4',
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/admin/userUpdate/isEnabled/` + email;
      window.location.replace(url);
    }
  });
}

async function isEmailVerified(email, status) {
  const action = status ? "mark as not verified" : "verify";
  const title = `Are you sure you want to ${action} this user's email?`;

  Swal.fire({
    title: title,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirm",
    customClass: {
      actions: 'flex justify-center space-x-4',
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/admin/userUpdate/isEmailVerified/` + email;
      window.location.replace(url);
    }
  });
}

async function isPhoneVerified(email, status) {
  const action = status ? "mark the phone number as unverified" : "verify the phone number";
  const title = `Are you sure you want to ${action}?`;
  Swal.fire({
    title: title,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirm",
    customClass: {
      actions: 'flex justify-center space-x-4',
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/admin/userUpdate/isPhoneVerified/` + email;
      window.location.replace(url);
    }
  });
}

async function deleteUser(email) {
  const title = `Are you sure you want to remove this User : ` + email;
  Swal.fire({
    title: title,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirm",
    customClass: {
      actions: 'flex justify-center space-x-4',
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/admin/userUpdate/deleteUser/` + email;
      window.location.replace(url);
    }
  });
}

async function openDescription(id, userId) {
  try {
    const queryData = await (await fetch(`${baseURL}/api/queries/${id}`)).json();
    const userData = await (await fetch(`${baseURL}/api/userData/${userId}`)).json();

    // Validate fetched data
    if (!queryData || !queryData.id) {
      console.warn("Invalid data for query", id);
      return;
    }

    // Populate modal with the fetched data
    document.querySelector("#query_id").textContent = queryData.id;
    document.querySelector("#image_url").href = queryData.image;
    document.querySelector("#query_title").textContent = queryData.title || "No title available";
    document.querySelector("#query_description").textContent = queryData.content || "No description available";
    document.querySelector("#query_creator_name").textContent = queryData.name || "Unknown";
    document.querySelector("#query_creator_realName").textContent = userData.name || "Unknown";
    document.querySelector("#query_creator_email").textContent = userData.email || "Unknown";
    document.querySelector("#query_created_date").textContent = new Date(queryData.date).toLocaleDateString() || "Unknown";
    document.querySelector("#query_status").textContent = queryData.resolved ? "Resolved" : "Not Resolved";

    const queryImage = document.querySelector("#query_image");
    queryImage.src = queryData.image || 'https://flowbite.com/docs/images/logo.svg';
    openQueryModal();

    const row = document.querySelector(`tr[data-id='${id}']`);
    if (row) {
      row.classList.toggle("font-bold");
    }
  } catch (error) {
    console.warn("Error fetching query data: ", error);
  }
}
async function openDescription1(id) {
  try {
    const queryData = await (await fetch(`${baseURL}/api/queries/${id}`)).json();
    // Validate fetched data
    if (!queryData || !queryData.id) {
      console.warn("Invalid data for query", id);
      return;
    }

    // Populate modal with the fetched data
    document.querySelector("#query_id").textContent = queryData.id;
    document.querySelector("#image_url").href = queryData.image;
    document.querySelector("#query_title").textContent = queryData.title || "No title available";
    document.querySelector("#query_description").textContent = queryData.content || "No description available";
    document.querySelector("#query_creator_name").textContent = queryData.name || "Unknown";
    document.querySelector("#query_creator_realName").textContent = "Unknown";
    document.querySelector("#query_creator_email").textContent = "Unknown";
    document.querySelector("#query_created_date").textContent = new Date(queryData.date).toLocaleDateString() || "Unknown";
    document.querySelector("#query_status").textContent = queryData.resolved ? "Resolved" : "Not Resolved";

    const queryImage = document.querySelector("#query_image");
    queryImage.src = queryData.image || 'https://flowbite.com/docs/images/logo.svg';
    openQueryModal();

    const row = document.querySelector(`tr[data-id='${id}']`);
    if (row) {
      row.classList.toggle("font-bold");
    }
  } catch (error) {
    console.warn("Error fetching query data: ", error);
  }
}

async function queryResolved(id){
  const title = `Are you sure you want to mark RESOLVED this query : ` + id;
  Swal.fire({
    title: title,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirm",
    customClass: {
      actions: 'flex justify-center space-x-4',
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/admin/query/queryResolved/` + id;
      window.location.replace(url);
    }
  });
}

async function deleteQuery(id){
  const title = `Are you sure you want to delete this query : ` + id;
  Swal.fire({
    title: title,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirm",
    customClass: {
      actions: 'flex justify-center space-x-4',
      confirmButton: 'custom-confirm-button',
      cancelButton: 'custom-cancel-button'
    },
    buttonsStyling: false
  }).then((result) => {
    if (result.isConfirmed) {
      const url = `${baseURL}/admin/query/queryDelete/` + id;
      window.location.replace(url);
    }
  });
}

document.getElementById("status-filter").addEventListener("change", function () {
  const selectedStatus = this.value;
  const tableRows = document.querySelectorAll("#query-table tbody tr");

  tableRows.forEach(row => {
    const statusCell = row.querySelector("td:nth-child(6) span"); // Adjust the column index if needed
    const isResolved = statusCell && statusCell.textContent.trim().toLowerCase() === "resolved";

    if (selectedStatus === "all") {
      row.style.display = ""; // Show all rows
    } else if (selectedStatus === "resolved" && isResolved) {
      row.style.display = ""; // Show only resolved rows
    } else if (selectedStatus === "not-resolved" && !isResolved) {
      row.style.display = ""; // Show only not resolved rows
    } else {
      row.style.display = "none"; // Hide rows not matching the filter
    }
  });
});


